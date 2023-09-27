package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.Field;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis.TRANSLATION_TOLERANCE;

public class MoveToPose extends SwerveCommand {
	/**
	 * get to a given pose with 3 pidControllers - x, y, rotational<3
	 */
	private ProfiledPIDController xController;
	private ProfiledPIDController yController;
	private ProfiledPIDController rotationController;
	protected Pose2d pose;

	public static final double ROT_KP = 2;
	public static final double ROT_KI = 0;
	public static final double ROT_KD = 0;

	public static final double TRANSLATION_KP = 0.5;
	public static final double TRANSLATION_KI = 0;
	public static final double TRANSLATION_KD = 0;

	private final double TOLERANCE = 0.05;
	private final double ROTATION_TOLERANCE = 4;
	private boolean DEBUG = false;
	
	private boolean useAlliance = false;

	public MoveToPose(Pose2d pose, boolean useAlliance) {
		this.pose = pose;
		this.useAlliance = useAlliance;
		xController = new ProfiledPIDController(TRANSLATION_KP, TRANSLATION_KI, TRANSLATION_KD, RobotMap.Swerve.Autonomus.constraints);
		yController = new ProfiledPIDController(TRANSLATION_KP, TRANSLATION_KI, TRANSLATION_KD, RobotMap.Swerve.Autonomus.constraints);
		rotationController = new ProfiledPIDController(ROT_KP, ROT_KI, ROT_KD, RobotMap.Swerve.Autonomus.constraints);
		rotationController.enableContinuousInput(-Math.PI, Math.PI);
		xController.setTolerance(TRANSLATION_TOLERANCE);
		yController.setTolerance(TRANSLATION_TOLERANCE);
		rotationController.setTolerance(Units.degreesToRadians(SwerveChassis.ROTATION_TOLERANCE));

	}
	
	public  MoveToPose(Pose2d pose){
		this(pose, false);
	}

	@Override
	public void initialize() {
		if (useAlliance && DriverStation.getAlliance() == DriverStation.Alliance.Red){
			pose = Field.mirrorPositionToOtherSide(pose);
		}
		xController.reset(swerve.getRobotPose().getX(), swerve.getChassisSpeeds().vxMetersPerSecond);
		yController.reset(swerve.getRobotPose().getY(), swerve.getChassisSpeeds().vyMetersPerSecond);
		rotationController.reset(swerve.getRobotPose().getRotation().getRadians(), swerve.getChassisSpeeds().omegaRadiansPerSecond);
		xController.setGoal(new TrapezoidProfile.State(pose.getX(), 0));
		yController.setGoal(new TrapezoidProfile.State(pose.getY(), 0));
		rotationController.setGoal(new TrapezoidProfile.State(pose.getRotation().getRadians(), 0));

	}

	public void debugDashboard(double xCalc, double yCalc, double rotationCalc) {
		SmartDashboard.putBoolean("at goal x", xController.atGoal());
		SmartDashboard.putBoolean("at goal y", yController.atGoal());
		SmartDashboard.putBoolean("at goal angle", rotationController.atGoal());
		SmartDashboard.putNumber("x cal", xCalc);
		SmartDashboard.putNumber("y cal", yCalc);
		SmartDashboard.putNumber("rot cal", rotationCalc);
		SmartDashboard.putNumber("tol", xController.getPositionTolerance());

	}

	@Override
	public void execute() {
		double xAxisPid = xController.calculate(SwerveChassis.getInstance().getRobotPose().getX());
		xAxisPid = xController.atGoal() ? 0 : xAxisPid;

		double yAxisPid = yController.calculate(SwerveChassis.getInstance().getRobotPose().getY());
		yAxisPid = yController.atGoal() ? 0 : yAxisPid;

		double rotationPid = rotationController.calculate(SwerveChassis.getInstance().getChassisAngle());
		rotationPid = rotationController.atGoal() ? 0 : rotationPid;

		swerve.moveByChassisSpeeds(
				xAxisPid,
				yAxisPid,
				rotationPid,
				SwerveChassis.getInstance().getChassisAngle()
		);

		if (DEBUG) {
			this.debugDashboard(xAxisPid, yAxisPid, rotationPid);
		}
	}

	@Override
	public boolean isFinished() {
		return xController.atGoal() && yController.atGoal() && rotationController.atGoal();

	}
}
