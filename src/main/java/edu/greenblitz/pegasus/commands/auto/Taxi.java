package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.swerve.SwerveCommand;

public class Taxi extends SwerveCommand {
	private long time0;
	private long time1;
	private long totalTime;
	private double speed;
	public Taxi(double timeInSecs, double speedMeterPerSecondBackwards){
		this.totalTime = (long) (timeInSecs * 1000);
		this.speed = speedMeterPerSecondBackwards;
	}
	@Override
	public void initialize() {
		time0 = System.currentTimeMillis();
	}
	
	@Override
	public void execute() {
		swerve.moveByChassisSpeeds(-speed,0,0,0);
		time1 = System.currentTimeMillis();
	}
	
	@Override
	public boolean isFinished() {
		return time1 - time0 > totalTime;
	}
	
	@Override
	public void end(boolean interrupted) {
		swerve.stop();
	}
}
