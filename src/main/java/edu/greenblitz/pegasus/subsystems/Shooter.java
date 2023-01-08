package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.pegasus.utils.motors.GBSparkMax;

public class Shooter extends GBSubsystem {
	
	private final static double RPM = 3000;
	private int flipped = 1;
	private final GBSparkMax motor;
	private boolean preparedToShoot;
	private boolean isShooter; //todo
	
	private static Shooter instance;
	
	private Shooter(int id) {
		this.motor = new GBSparkMax(id, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.config(RobotMap.Pegasus.Shooter.ShooterMotor.shooterConf);
		preparedToShoot = false;
	}
	
	public static void create(int id) {
		instance = new Shooter(id);
	}
	
	public static Shooter getInstance() {
		return instance;
	}
	
	public void setPower(double power) {
		this.motor.set(power);
	}
	
	public void setIdleMode(CANSparkMax.IdleMode idleMode) {
		motor.setIdleMode(idleMode);
	}
	
	/**
	 * pid is handled by GBMotor either internal motor controller pid or external in GBMotor implementation
	 *
	 * @param target the target speed in rpm
	 */
	public void setSpeedByPID(double target) {
//		System.out.println(target);
		motor.getPIDController().setReference(target * flipped, CANSparkMax.ControlType.kVelocity, 0, RobotMap.Pegasus.Shooter.ShooterMotor.feedforward.calculate(target), SparkMaxPIDController.ArbFFUnits.kVoltage);
		//
	}
	
	public void setPIDConsts(PIDObject obj) {
		motor.configPID(obj);
		
	}
	
	public double getShooterSpeed() {
		return motor.getEncoder().getVelocity();
	}
	
	public void resetEncoder() {
		motor.getEncoder().setPosition(0);
	}
	
	public boolean isPreparedToShoot() { //todo make this shooter independent
		return preparedToShoot;
	}
	
	public void setPreparedToShoot(boolean preparedToShoot) {
		this.preparedToShoot = preparedToShoot;
	}
	
	public void flip() {
		this.flipped *= -1;
	}
	
	public int getFlipped() {
		return this.flipped;
	}
	
	
}
