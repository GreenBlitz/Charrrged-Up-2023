package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShootBlue extends GBCommand {
// This is done

	public ShootBlue(){
		require(MyShooter.getInstace());
	}

	@Override
	public void execute() {
		MyShooter.getInstace().setMiddlePower(-0.2 * MyShooter.getInstace().speed);
		MyShooter.getInstace().setUpperPower(-0.2 * MyShooter.getInstace().speed);
		MyShooter.getInstace().timer.start();
	}

	@Override
	public boolean isFinished() {
		//Need for the code to get to end, don't forget!
		if (MyShooter.getInstace().timer.advanceIfElapsed(0.5)){
			return true;
		}
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		MyShooter.getInstace().setMiddlePower(0);
		MyShooter.getInstace().setUpperPower(0);
		MyShooter.getInstace().timer.stop();
		MyShooter.getInstace().timer.reset();
	}
}
