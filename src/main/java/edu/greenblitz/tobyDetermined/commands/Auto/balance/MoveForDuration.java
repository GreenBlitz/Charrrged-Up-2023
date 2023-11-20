package edu.greenblitz.tobyDetermined.commands.Auto.balance;

import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.wpi.first.wpilibj.Timer;

public class MoveForDuration extends SwerveCommand {

	double duration;
	double speed;
	Timer timer;

	public MoveForDuration(double duration, double speed) {
		this.duration = duration;
		this.speed = speed;
		timer = new Timer();
	}

	@Override
	public void initialize() {
		timer.start();
	}

	@Override
	public void execute() {
		swerve.moveByChassisSpeeds(speed, 0, 0, swerve.getGyro().getYaw());
	}

	@Override
	public boolean isFinished() {
		return timer.advanceIfElapsed(duration);
	}

	@Override
	public void end(boolean interrupted) {
		swerve.stop();
	}


}
