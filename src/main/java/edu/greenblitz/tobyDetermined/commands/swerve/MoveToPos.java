package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveToPos extends SwerveCommand {
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

        xController = new ProfiledPIDController(2,0,0,RobotMap.Swerve.Autonomus.constraints);
        yController = new ProfiledPIDController(2,0,0,RobotMap.Swerve.Autonomus.constraints);
        rotationController = new ProfiledPIDController(2,0,0,RobotMap.Swerve.Autonomus.constraints);
        rotationController.enableContinuousInput(-Math.PI,Math.PI);
        xController.setTolerance(0.2);
        yController.setTolerance(0.2);
        rotationController.setTolerance(Units.degreesToRadians(5));

    }

    @Override
    public void initialize() {
        super.initialize();
        xController.reset(swerve.getRobotPose().getX());
        yController.reset(swerve.getRobotPose().getY());
        rotationController.reset(swerve.getRobotPose().getRotation().getRadians());
        xController.setGoal(new TrapezoidProfile.State(pos.getX(), 0));
        yController.setGoal(new TrapezoidProfile.State(pos.getY(), 0));
        rotationController.setGoal(new TrapezoidProfile.State(pos.getRotation().getRadians(), 0));

    }

    @Override
    public void execute() {

/*
        xTrap = new TrapezoidProfile(RobotMap.Swerve.Autonomus.constraints, new TrapezoidProfile.State(pos.getX(), 0), new TrapezoidProfile.State(SwerveChassis.getInstance().getRobotPose().getX(), SwerveChassis.getInstance().getChassisSpeeds().vxMetersPerSecond));
        yTrap = new TrapezoidProfile(RobotMap.Swerve.Autonomus.constraints, new TrapezoidProfile.State(pos.getY(), 0), new TrapezoidProfile.State(SwerveChassis.getInstance().getRobotPose().getY(), SwerveChassis.getInstance().getChassisSpeeds().vyMetersPerSecond));
        rotationTrap = new TrapezoidProfile(RobotMap.Swerve.Autonomus.constraints, new TrapezoidProfile.State(pos.getRotation().getRadians(), 0), new TrapezoidProfile.State(SwerveChassis.getInstance().getRobotPose().getRotation().getRadians(), SwerveChassis.getInstance().getChassisSpeeds().omegaRadiansPerSecond));
*/

        double xCal = xController.atGoal() ? 0 : xController.calculate(SwerveChassis.getInstance().getRobotPose().getX());
        double yCal = yController.atGoal() ? 0 : yController.calculate(SwerveChassis.getInstance().getRobotPose().getY());
        double rotationCal = rotationController.atGoal() ? 0 : rotationController.calculate(swerve.getChassisAngle());
        swerve.moveByChassisSpeeds(
                xCal,
                yCal,
                rotationCal,
                SwerveChassis.getInstance().getChassisAngle()
        );
        SmartDashboard.putBoolean("at goal x",xController.atGoal());
        SmartDashboard.putBoolean("at goal y",yController.atGoal());
        SmartDashboard.putNumber("x cal",xCal);
        SmartDashboard.putNumber("y goal", yController.getGoal().position);
        SmartDashboard.putNumber("y cal",yCal);
        SmartDashboard.putNumber("rot cal",rotationCal);
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || (xController.atGoal() && yController.atGoal() && rotationController.atGoal());
    }
}
