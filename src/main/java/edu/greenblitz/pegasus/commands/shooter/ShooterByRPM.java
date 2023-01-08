package edu.greenblitz.pegasus.commands.shooter;

public class ShooterByRPM extends ShooterCommand {
	private static final double EPSILON = 50;
	private static final double APPROXIMATE_INSERTION_TIME = 5; //approximate amount of roborio cycles between funnel activation and shooting
	private double lastSpeed;
	protected double target;
	protected double tStart;

	public ShooterByRPM(double target) {
		this.target = target;
	}
	
	public ShooterByRPM(double target,int inShootingSpeedMin ) {
		this(target);
	}


	@Override
	public void initialize() {
		shooter.setPreparedToShoot(false);
		tStart = System.currentTimeMillis() / 1000.0;
		lastSpeed = shooter.getShooterSpeed();
	}

	double accel;
	@Override
	public void execute() {
		shooter.setSpeedByPID(target);
		accel = lastSpeed - shooter.getShooterSpeed();

		shooter.setPreparedToShoot((Math.abs(shooter.getShooterSpeed() - target) < EPSILON
				&& Math.abs(shooter.getShooterSpeed() - target + (accel*APPROXIMATE_INSERTION_TIME)) < EPSILON));
		lastSpeed = shooter.getShooterSpeed();

	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		shooter.setPreparedToShoot(false);
		shooter.setSpeedByPID(0);  // todo find a solution that allows for double shoot
		super.end(interrupted);
	}
	

}
