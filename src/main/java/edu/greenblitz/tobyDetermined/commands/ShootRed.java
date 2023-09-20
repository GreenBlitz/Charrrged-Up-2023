package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.utils.GBCommand;

public class ShootRed extends GBCommand {

	@Override
	public void execute() {
		MyShooter.getInstance().setMiddlePower(-0.2 * MyShooter.getInstance().speed);
		MyShooter.getInstance().setUpperPower(-0.2 * MyShooter.getInstance().speed);
		MyShooter.getInstance().redIn = false;
	}

	@Override
	public void end(boolean interrupted) {
		MyShooter.getInstance().setMiddlePower(0);
		MyShooter.getInstance().setUpperPower(0);
	}
}
