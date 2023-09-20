package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.utils.GBCommand;

public class ShootBall extends GBCommand {

	@Override
	public void execute() {
		MyShooter.getInstance().setUpperPower(0.3);
	}

	@Override
	public void end(boolean interrupted) {
		MyShooter.getInstance().setUpperPower(0);
	}
}
