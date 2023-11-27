package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.CurrentNode;
import edu.greenblitz.tobyDetermined.Nodesssss.MidNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.GBMath;
import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;

import java.util.LinkedList;

public class MoveArmByTrajectory extends GBCommand {
	
	private Trajectory path;
	private Timer clock;
	private Extender extender;
	private Elbow elbow;
	private double prevX;
	private double prevY;
	private int currentIndexInPosList = 0;
	private LinkedList<Translation2d> posList;
	private LinkedList<RobotMap.TelescopicArm.PresetPositions> nodeList;
	private static final double TOLERANCE_TO_NODE = 0.04;//meters
	private static final double STARTING_LENGTH = RobotMap.TelescopicArm.Extender.STARTING_LENGTH;
	private static final HolonomicDriveController controller =  new HolonomicDriveController(
			new PIDController(0.1, 0, 0),
			new PIDController(0.1, 0, 0),
			new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(6.28, 3.14))
	);
	public MoveArmByTrajectory(Trajectory path, LinkedList<Translation2d> posList,LinkedList<RobotMap.TelescopicArm.PresetPositions> nodeList) {
		extender = Extender.getInstance();
		elbow = Elbow.getInstance();
		require(extender);
		require(elbow);
		
		this.posList = posList;
		this.nodeList = nodeList;
		this.path = path;
	}
	
	@Override
	public void initialize() {
		clock = new Timer();
		clock.start();
		Translation2d cords = GBMath.polarToCartesian(extender.getLength()+STARTING_LENGTH,elbow.getAngleRadians());
		prevX = cords.getX();
		prevY = cords.getY();
	}
	
	@Override
	public void execute() {
		Translation2d cords = GBMath.polarToCartesian(extender.getLength() + STARTING_LENGTH, elbow.getAngleRadians());
		Trajectory.State goal = path.sample(clock.get());// need to add 50 miliseconds!!!
		if (currentIndexInPosList >= posList.size())//change to pose-1 too!!!
			currentIndexInPosList = posList.size() - 1;

		//very weird all of that down{!!!
		double distanceToCurrentNode = cords.getDistance(posList.get(currentIndexInPosList));
		double distanceToNextNode;
		if (currentIndexInPosList < posList.size() - 1)
			distanceToNextNode = cords.getDistance(posList.get(currentIndexInPosList + 1));
		else
			distanceToNextNode = distanceToCurrentNode;
		if (distanceToCurrentNode <= TOLERANCE_TO_NODE)
			currentIndexInPosList++;
		if (distanceToNextNode <= TOLERANCE_TO_NODE)
			currentIndexInPosList += 2;
		ChassisSpeeds speeds = controller.calculate(new Pose2d(cords, new Rotation2d(0, 0)), goal, goal.poseMeters.getRotation());
		double x = goal.poseMeters.getX();
		double y = goal.poseMeters.getY();
		//}!!

		double speedX = 0;//no need for speed conversion, distance works
		double speedY = 0;//great because its in meters/second
		NodeArm target = NodeBase.getNode(nodeList.getLast());
		if (target.getIsAtNode(extender.getLength(),elbow.getAngleRadians())) {// condition is needed a !
			speedX = (cords.getX() - prevX);
			speedY = (cords.getY() - prevY);
		}
		//conversion between cartesian and polar speeds using complicated math
		double extenderVelocity = x* (speeds.vxMetersPerSecond+speedX) + y * (speeds.vyMetersPerSecond+speedY);
		extenderVelocity /= Math.sqrt(x*x + y*y);

		double elbowVelocity = x*(speeds.vyMetersPerSecond+speedY)-y*(speeds.vxMetersPerSecond+speedX);
		elbowVelocity /= x*x+y*y;
		
		prevX = cords.getX();
		prevY = cords.getY();
		
		extender.setMotorVoltage(Extender.getDynamicFeedForward(extenderVelocity,Elbow.getInstance().getAngleRadians()));
		elbow.setMotorVoltage(Elbow.getDynamicFeedForward(elbowVelocity,extender.getLength(),elbow.getAngleRadians()));
	}
	
	@Override
	public boolean isFinished() {
		NodeArm target = NodeBase.getNode(nodeList.getLast());
		return clock.get() >= (path.getTotalTimeSeconds()) && target.getIsAtNode(extender.getLength(),elbow.getAngleRadians());
	}
	
	@Override
	public void end(boolean interrupted) {
		if (interrupted) {
			if (currentIndexInPosList >= nodeList.size())
				currentIndexInPosList = nodeList.size()-1;
			int nextIndexInPosList = currentIndexInPosList + 1;
			if (nextIndexInPosList >= nodeList.size())
				nextIndexInPosList = nodeList.size()-1;
			MidNode.getInstance().setNewMidNode(
					nodeList.get(currentIndexInPosList),
					nodeList.get(nextIndexInPosList),
					extender.getLength()+ STARTING_LENGTH,
					elbow.getAngleRadians()
			);
			CurrentNode.setCurrentNode(RobotMap.TelescopicArm.PresetPositions.MID_NODE);
		}
		else
			CurrentNode.setCurrentNode(nodeList.getLast());
		elbow.stop();
		extender.stop();
	}
}
