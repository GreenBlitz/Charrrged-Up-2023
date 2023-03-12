package edu.greenblitz.tobyDetermined.commands.Auto.balance.bangBangBalance;

import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeExtender;
import edu.wpi.first.math.filter.Debouncer;

public class GetOnRamp extends SwerveCommand {
	
	private double speed;
	private  double usedSpeed;
	private boolean forwards;
	private static final double TOLERANCE = Math.toRadians(17);
	
	private Debouncer debouncer;
	
	
	double pitchAngle;
	public GetOnRamp(double speed){
		require(IntakeExtender.getInstance());
		this.speed =speed;
		forwards = speed > 0;
	}
	
	@Override
	public void initialize() {
		super.initialize();
		pitchAngle =0;
		debouncer = new Debouncer(0.1);
		usedSpeed = speed;
		IntakeExtender.getInstance().extend();
		
	}
	
	@Override
	public void execute() {
		super.execute();
		pitchAngle = swerve.getPigeonGyro().getRoll() * (forwards ? 1:-1);//gyro is flipped
		swerve.moveByChassisSpeeds(usedSpeed,0,0,0);
		usedSpeed+= Math.signum(speed)* 0.02;
	}
	
	@Override
	public boolean isFinished() {
		
		return super.isFinished() || debouncer.calculate(Math.abs(pitchAngle) > TOLERANCE) ;
	}
}
