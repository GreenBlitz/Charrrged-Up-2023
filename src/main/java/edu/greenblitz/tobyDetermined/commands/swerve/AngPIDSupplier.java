package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.subsystems.Photonvision;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.greenblitz.utils.PIDObject;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.DoubleSupplier;

public class AngPIDSupplier implements DoubleSupplier {
	
	
	public static final PIDObject ROTATION_PID = new PIDObject().withKp(3.6).withKi(0);
	private Translation2d targetLoc;
	private PIDController chassisPid;
	
	public AngPIDSupplier(Translation2d targetLoc) {
		SmartDashboard.putNumber("kp", 1);
		SmartDashboard.putNumber("ki", 0);
		SmartDashboard.putNumber("kd", 0);
		this.targetLoc = targetLoc;
		chassisPid = new PIDController(ROTATION_PID.getKp(), ROTATION_PID.getKi(), ROTATION_PID.getKd());
		chassisPid.enableContinuousInput(0, 2 * Math.PI); //min and max
	}
	
	/**
	 * vx - the speed of the robot in x.
	 * vy - the speed of the robot in y.
	 * dx - the x distance from the vision target.
	 * dy - the y distance from the vision target.
	 * all in wpilib coordinates
	 *
	 * @return the equitation of the feedforward(more math in this site: https://tinyurl.com/visionGB).
	 */
	private double getAngVelDiffByVision() {
		double vy = SwerveChassis.getInstance().getChassisSpeeds().vyMetersPerSecond;
		double vx = SwerveChassis.getInstance().getChassisSpeeds().vxMetersPerSecond;
		double dx = Photonvision.getInstance().targetPos().getX();
		double dy = Photonvision.getInstance().targetPos().getY();
		if (dx == 0 || dy == 0) {
			return 0;
		}
		return -((vy * dx + vx * dy) / (dx * dx + dy * dy));
	}
	
	@Override
	public double getAsDouble() {
		double kp = SmartDashboard.getNumber("kp", 1);
		double kd = SmartDashboard.getNumber("kd", 0);
		double ki = SmartDashboard.getNumber("ki", 0);
		Translation2d relativeLoc = targetLoc.minus(SwerveChassis.getInstance().getRobotPose().getTranslation());
		double ang = Math.IEEEremainder(Math.atan2(relativeLoc.getY(), relativeLoc.getX()), 2 * Math.PI);
		chassisPid.setPID(kp, ki, kd);
		return chassisPid.calculate(SwerveChassis.getInstance().getChassisAngle(), ang);
	}
}
