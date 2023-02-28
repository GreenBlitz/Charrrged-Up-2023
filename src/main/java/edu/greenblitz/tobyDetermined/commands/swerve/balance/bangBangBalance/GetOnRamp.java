package edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance;

import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance.BangBangBalance;
import edu.wpi.first.math.filter.Debouncer;

public class GetOnRamp extends SwerveCommand {
	
	private double speed;
	private boolean forwards;
	private static final double TOLERANCE = BangBangBalance.TOLERANCE;
	
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
		
	}
	
	@Override
	public void execute() {
		super.execute();
		pitchAngle = swerve.getPigeonGyro().getRoll() * (forwards ? 1:-1);//gyro is flipped
		swerve.moveByChassisSpeeds(speed,0,0,0);
	}
	
	@Override
	public boolean isFinished() {
		
		return super.isFinished() || debouncer.calculate(Math.abs(pitchAngle) > TOLERANCE) ;
	}
}
