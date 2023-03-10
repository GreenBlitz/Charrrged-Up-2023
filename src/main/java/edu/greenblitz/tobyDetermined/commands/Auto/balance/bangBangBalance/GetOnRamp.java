package edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance;

import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.wpi.first.math.filter.Debouncer;

public class GetOnRamp extends SwerveCommand {
	
	private double speed;
	private  double usedSpeed;
	private boolean forwards;
	private static final double TOLERANCE = Math.toRadians(8);
	
	private Debouncer debouncer;
	
	
	double pitchAngle;
	public GetOnRamp(double speed){
		this.speed =speed;
		forwards = speed > 0;
	}
	
	@Override
	public void initialize() {
		super.initialize();
		pitchAngle =0;
		debouncer = new Debouncer(0.1);
		usedSpeed = speed;
		
	}
	
	@Override
	public void execute() {
		super.execute();
		pitchAngle = swerve.getPigeonGyro().getRoll() * (forwards ? 1:-1);//gyro is flipped
		swerve.moveByChassisSpeeds(usedSpeed,0,0,0);
		usedSpeed+=0.005;
	}
	
	@Override
	public boolean isFinished() {
		
		return super.isFinished() || debouncer.calculate(Math.abs(pitchAngle) > TOLERANCE) ;
	}
}
