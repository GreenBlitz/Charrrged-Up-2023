package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveToPos extends GBCommand {
    private ProfiledPIDController xController;
    private TrapezoidProfile xTrap;
    private TrapezoidProfile yTrap;
    private TrapezoidProfile rotationTrap;
    private ProfiledPIDController yController;
    private ProfiledPIDController rotationController;
    private Pose2d pos;

    public MoveToPos(Pose2d pos) {
        SmartDashboard.putBoolean("did push", true);
        this.pos = pos;
//        xController = new ProfiledPIDController(RobotMap.Swerve.Autonomus.translationKp, RobotMap.Swerve.Autonomus.translationKi, RobotMap.Swerve.Autonomus.translationKd, RobotMap.Swerve.Autonomus.constraints);
//        xController.setGoal(pos.getX());
//        yController = new ProfiledPIDController(RobotMap.Swerve.Autonomus.translationKp, RobotMap.Swerve.Autonomus.translationKi, RobotMap.Swerve.Autonomus.translationKd, RobotMap.Swerve.Autonomus.constraints);
//        yController.setGoal(pos.getY());
//        rotationController = new ProfiledPIDController(RobotMap.Swerve.Autonomus.rotKp, RobotMap.Swerve.Autonomus.rotKi, RobotMap.Swerve.Autonomus.rotKd, RobotMap.Swerve.Autonomus.constraints);
//        rotationController.setGoal(pos.getRotation().getRadians());
//        rotationController.enableContinuousInput(0, 2 * Math.PI);
    }

    @Override
    public void execute() {
        xTrap = new TrapezoidProfile(RobotMap.Swerve.Autonomus.constraints, new TrapezoidProfile.State(pos.getX(), 0), new TrapezoidProfile.State(SwerveChassis.getInstance().getRobotPose().getX(), SwerveChassis.getInstance().getChassisSpeeds().vxMetersPerSecond));
        yTrap = new TrapezoidProfile(RobotMap.Swerve.Autonomus.constraints, new TrapezoidProfile.State(pos.getY(), 0), new TrapezoidProfile.State(SwerveChassis.getInstance().getRobotPose().getY(), SwerveChassis.getInstance().getChassisSpeeds().vyMetersPerSecond));
        rotationTrap = new TrapezoidProfile(RobotMap.Swerve.Autonomus.constraints, new TrapezoidProfile.State(pos.getRotation().getRadians(), 0), new TrapezoidProfile.State(SwerveChassis.getInstance().getRobotPose().getRotation().getRadians(), SwerveChassis.getInstance().getChassisSpeeds().omegaRadiansPerSecond));

        SwerveChassis.getInstance().moveByChassisSpeeds(
                //xController.calculate(SwerveChassis.getInstance().getRobotPose().getX()),
                xTrap.calculate(0).velocity,
                yTrap.calculate(0).velocity,
                rotationTrap.calculate(0).velocity,
//                yController.calculate(SwerveChassis.getInstance().getRobotPose().getY()),
//                rotationController.calculate(SwerveChassis.getInstance().getRobotPose().getRotation().getRadians()),
                SwerveChassis.getInstance().getChassisAngle()
        );
        SmartDashboard.putNumber("xTrap", xTrap.calculate(0).velocity);
        SmartDashboard.putNumber("yTrap", xTrap.calculate(0).velocity);
        SmartDashboard.putNumber("rotation", xTrap.calculate(0).velocity);
    }
}
