package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveToPos extends SwerveCommand {
    /**
     * get to a given pose with 3 pidControllers - x, y, rotational<3
     */
    private ProfiledPIDController xController;
    private ProfiledPIDController yController;
    private ProfiledPIDController rotationController;
    private Pose2d pos;

    public static final double rotKp = 2;
    public static final double rotKi = 0;
    public static final double rotKd = 0;

    public static final double translationKp = 2;
    public static final double translationKi = 0;
    public static final double translationKd = 0;
    
    private double tolerance = 0.05;
    private double rotationTolerance = 4;
    private boolean debugDash = false;

    public MoveToPos(Pose2d pos) {
        this.pos = pos;
        xController = new ProfiledPIDController(translationKp, translationKi, translationKd, RobotMap.Swerve.Autonomus.constraints);
        yController = new ProfiledPIDController(translationKp, translationKi, translationKd, RobotMap.Swerve.Autonomus.constraints);
        rotationController = new ProfiledPIDController(rotKp, rotKi, rotKd, RobotMap.Swerve.Autonomus.constraints);
        rotationController.enableContinuousInput(-Math.PI, Math.PI);
        xController.setTolerance(tolerance);
        yController.setTolerance(tolerance);
        rotationController.setTolerance(Units.degreesToRadians(rotationTolerance));

    }
    
    public MoveToPos(Pose2d pos, boolean isDebug){
        this(pos);
        this.debugDash = isDebug;
    }

    @Override
    public void initialize() {
        xController.reset(swerve.getRobotPose().getX(), swerve.getChassisSpeeds().vxMetersPerSecond);
        yController.reset(swerve.getRobotPose().getY(), swerve.getChassisSpeeds().vyMetersPerSecond);
        rotationController.reset(swerve.getRobotPose().getRotation().getRadians(), swerve.getChassisSpeeds().omegaRadiansPerSecond);
        xController.setGoal(new TrapezoidProfile.State(pos.getX(), 0));
        yController.setGoal(new TrapezoidProfile.State(pos.getY(), 0));
        rotationController.setGoal(new TrapezoidProfile.State(pos.getRotation().getRadians(), 0));
        
    }
    
    public void debugDashboard(double xCalc, double yCalc, double rotationCalc){
        SmartDashboard.putBoolean("at goal x",xController.atGoal());
        SmartDashboard.putBoolean("at goal y",yController.atGoal());
        SmartDashboard.putBoolean("at goal angle",rotationController.atGoal());
        SmartDashboard.putNumber("x cal",xCalc);
        SmartDashboard.putNumber("y cal",yCalc);
        SmartDashboard.putNumber("rot cal",rotationCalc);
        
    }

    @Override
    public void execute() {
        double xCalc = xController.atGoal() ? 0 : xController.calculate(SwerveChassis.getInstance().getRobotPose().getX());
        double yCalc = yController.atGoal() ? 0 : yController.calculate(SwerveChassis.getInstance().getRobotPose().getY());
        double rotationCalc = rotationController.atGoal() ? 0 : rotationController.calculate(swerve.getChassisAngle());
        swerve.moveByChassisSpeeds(
                xCalc,
                yCalc,
                rotationCalc,
                SwerveChassis.getInstance().getChassisAngle()
        );
        if(debugDash) { this.debugDashboard(xCalc, yCalc, rotationCalc); }
        
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || (xController.atGoal() && yController.atGoal() && rotationController.atGoal());
        
    }
}
