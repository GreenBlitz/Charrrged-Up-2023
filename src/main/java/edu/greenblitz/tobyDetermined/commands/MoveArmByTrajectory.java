package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.GBMath;
import edu.wpi.first.math.Pair;
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

public class MoveArmByTrajectory extends GBCommand {
	
	private Trajectory path;
	private Timer clock;
	private Extender extender;
	private Elbow elbow;
	private static final double STARTING_LENGTH = RobotMap.TelescopicArm.Extender.STARTING_LENGTH;
	private static final HolonomicDriveController controller =  new HolonomicDriveController(
			new PIDController(1, 0, 0),
			new PIDController(1, 0, 0),
			new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(6.28, 3.14))
	);
	public MoveArmByTrajectory(Trajectory path) {
		extender = Extender.getInstance();
		elbow = Elbow.getInstance();
		require(extender);
		require(elbow);
		
		this.path = path;
	}
	
	@Override
	public void initialize() {
		clock = new Timer();
		clock.start();
	}
	
	@Override
	public void execute() {
		Translation2d cords = GBMath.polarToCartesian(extender.getLength()+STARTING_LENGTH,elbow.getAngleRadians());
		Trajectory.State goal = path.sample(clock.get());
		
		ChassisSpeeds speeds = controller.calculate(new Pose2d(cords,new Rotation2d(0,0)),goal,goal.poseMeters.getRotation());
		double x = goal.poseMeters.getX();
		double y = goal.poseMeters.getY();

//		Pair<Double,Double> speedsPolar = GBMath.cartesianToPolar(speeds.vxMetersPerSecond,speeds.vyMetersPerSecond);

		//conversion between cartesian and polar speeds using complicated math

		double extenderVelocity = x* speeds.vxMetersPerSecond + y * speeds.vyMetersPerSecond;
		extenderVelocity /= Math.sqrt(x*x + y*y);

		double elbowVelocity = x*speeds.vyMetersPerSecond-speeds.vxMetersPerSecond*y;
		elbowVelocity /= x*x+y*y;

		extender.setLinSpeed(extenderVelocity);
		elbow.setAngSpeed(elbowVelocity,elbow.getAngleRadians(),extender.getLength());
	}
	
	@Override
	public boolean isFinished() {
		return clock.get() >= path.getTotalTimeSeconds();
	}
	
	@Override
	public void end(boolean interrupted) {
		elbow.stop();
		extender.stop();
	}
}
