package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.utils.GBCommand;

public class ShootBlue extends GBCommand {
// This is done

	public ShootBlue(){
		require(MyShooter.getInstance());
	}

	@Override
	public void execute() {
		MyShooter.getInstance().setMiddlePower(-0.2 * MyShooter.getInstance().speed);
		MyShooter.getInstance().setUpperPower(-0.2 * MyShooter.getInstance().speed);
		MyShooter.getInstance().timer.start();
	}

	@Override
	public boolean isFinished() {
		//Need for the code to get to end, don't forget!
		if (MyShooter.getInstance().timer.advanceIfElapsed(0.5)){
			return true;
		}
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		MyShooter.getInstance().setMiddlePower(0);
		MyShooter.getInstance().setUpperPower(0);
		MyShooter.getInstance().timer.stop();
		MyShooter.getInstance().timer.reset();
	}
}
