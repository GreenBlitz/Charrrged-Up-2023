package edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance;

import edu.greenblitz.tobyDetermined.commands.ConsoleLog;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.MedianFilter;

public class BangBangBalance extends SwerveCommand {
	
	private double originalSpeed;
	private double usedSpeed;
	public static final double TOLERANCE = Math.toRadians(5);
	
	private Debouncer debouncer;
	
	double pitchAngle;
	private boolean forwards;
	
	public BangBangBalance(double speed){
		this.originalSpeed =speed;
		forwards = speed > 0;
	}
	
	@Override
	public void initialize() {
		super.initialize();
		Console.log("stuff", " nkl");
		pitchAngle =0;
		debouncer = new Debouncer(0.3);
		usedSpeed = originalSpeed;
	}
	
	@Override
	public void execute() {
		super.execute();
		if (pitchAngle * swerve.getPigeonGyro().getRoll() * (forwards ? -1:1) < 0){
			usedSpeed = 0.5 * usedSpeed;
		}
		pitchAngle = swerve.getPigeonGyro().getRoll() * (forwards ? -1:1);//gyro is flipped
		if (Math.abs(pitchAngle) > TOLERANCE){
			swerve.moveByChassisSpeeds(usedSpeed * Math.signum(pitchAngle), 0.0, 0.0, 0);
		}
		else {
			swerve.stop();
		}
	}
	
	@Override
	public boolean isFinished() {
		boolean debounced = debouncer.calculate(Math.abs(pitchAngle) < TOLERANCE);
		
		return super.isFinished() || debounced;
	}
}
