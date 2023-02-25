package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.wpi.first.wpilibj.Timer;

public class MoveDuration extends SwerveCommand {
	
	double duration;
	double speed;
	Timer timer;
	
	public MoveDuration(double duration, double speed){
		this.duration = duration;
		this.speed = speed;
		timer = new Timer();
	}

	@Override
	public void initialize(){
		timer.start();
	}
	
	@Override
	public void execute(){
		swerve.moveByChassisSpeeds(speed, 0, 0, swerve.getPigeonGyro().getYaw());
	}
	
	@Override
	public boolean isFinished(){
		return timer.advanceIfElapsed(duration);
	}
	
	@Override
	public void end(boolean interrupted){
		swerve.stop();
	}
	
	
	
}
