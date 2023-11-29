package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.CurrentNode;
import edu.greenblitz.tobyDetermined.Nodesssss.MidNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ArmSimulation;
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
import org.littletonrobotics.junction.Logger;

import java.util.LinkedList;

public class MoveArmByTrajectory extends GBCommand {

    private Trajectory path;
    private Timer clock;
    private Extender extender;
    private Elbow elbow;
    private double prevX;
    private double prevY;
    private LinkedList<Pair<Double, RobotMap.TelescopicArm.PresetPositions>> nodeTimeList;
    private static final double CORRECTION_SIZE = 0.3;//in m/s
    private static final double STARTING_LENGTH = RobotMap.TelescopicArm.Extender.STARTING_LENGTH;
    private static final HolonomicDriveController controller = new HolonomicDriveController(
            new PIDController(0.2, 0, 0),
            new PIDController(0.005, 0, 0),
            new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(6.28, 3.14))
    );

    public MoveArmByTrajectory(Trajectory path, LinkedList<Pair<Double, RobotMap.TelescopicArm.PresetPositions>> nodeTimeList) {
        extender = Extender.getInstance();
        elbow = Elbow.getInstance();
        require(extender);
        require(elbow);

        this.nodeTimeList = nodeTimeList;
        this.path = path;
    }

    @Override
    public void initialize() {
        clock = new Timer();
        clock.start();
        Translation2d cords = GBMath.convertToCartesian(extender.getLength() + STARTING_LENGTH, elbow.getAngleRadians());
        prevX = cords.getX();
        prevY = cords.getY();
    }

    @Override
    public void execute() {
        Translation2d cords = GBMath.convertToCartesian(extender.getLength() + STARTING_LENGTH, elbow.getAngleRadians());
        Trajectory.State goal = path.sample(clock.get() + 0.02);

        Pair<Double, Double> goalPolar = GBMath.convertToPolar(goal.poseMeters.getX(), goal.poseMeters.getY());
        Logger.getInstance().recordOutput("nextTrajectory", ArmSimulation.getArmPosition(goalPolar.getFirst() - STARTING_LENGTH, goalPolar.getSecond()));

        ChassisSpeeds speeds = controller.calculate(new Pose2d(cords, new Rotation2d(0, 0)), goal, goal.poseMeters.getRotation());
        double x = goal.poseMeters.getX();
        double y = goal.poseMeters.getY();

        double speedX = (cords.getX() - prevX) / CORRECTION_SIZE;
        ;//no need for speed conversion, distance works
        double speedY = (cords.getY() - prevY) / CORRECTION_SIZE;
        speedY = 0;
        speedX = 0;
        ;//great because its in meters/second

        //conversion between cartesian and polar speeds using complicated math
        double extenderVelocity = x * (speeds.vxMetersPerSecond + speedX) + y * (speeds.vyMetersPerSecond + speedY);
        extenderVelocity /= Math.sqrt(x * x + y * y);

        double elbowVelocity = x * (speeds.vyMetersPerSecond + speedY) - y * (speeds.vxMetersPerSecond + speedX);
        elbowVelocity /= x * x + y * y;
        elbowVelocity *= extender.getLength() + STARTING_LENGTH;

        prevX = cords.getX();
        prevY = cords.getY();
        extender.setMotorVoltage(Extender.getDynamicFeedForward(extenderVelocity, Elbow.getInstance().getAngleRadians()));
        elbow.setMotorVoltage(Elbow.getDynamicFeedForward(elbowVelocity, extender.getLength(), elbow.getAngleRadians()));
    }

    @Override
    public boolean isFinished() {
        NodeArm target = NodeBase.getNode(nodeTimeList.getLast().getSecond());
        return target.getIsAtNode(elbow.getAngleRadians(), extender.getLength());
    }

    @Override
    public void end(boolean interrupted) {
        if (clock.get() < path.getTotalTimeSeconds()) {
            setNewMidNodeByTimes();
        }
        CurrentNode.setCurrentNode(nodeTimeList.getLast().getSecond());
        elbow.stop();
        extender.stop();
    }

    private void setNewMidNodeByTimes() {
        RobotMap.TelescopicArm.PresetPositions start = nodeTimeList.get(nodeTimeList.size() - 2).getSecond();//second to last
        RobotMap.TelescopicArm.PresetPositions end = nodeTimeList.getLast().getSecond();//last
        for (int i = 0; i < nodeTimeList.size() - 1; i++) {
            if (nodeTimeList.get(i).getFirst() < clock.get() && nodeTimeList.get(i + 1).getFirst() >= clock.get()) {
                start = nodeTimeList.get(i).getSecond();
                end = nodeTimeList.get(i + 1).getSecond();
                break;
            }
        }

        MidNode.getInstance().setNewMidNode(start, end, extender.getLength(), elbow.getAngleRadians());
    }
}
