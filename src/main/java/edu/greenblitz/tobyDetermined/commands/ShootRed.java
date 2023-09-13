package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.utils.GBCommand;

public class ShootRed extends GBCommand {

	@Override
	public void execute() {
		MyShooter.getInstace().activateShooterRed();
	}

	@Override
	public void end(boolean interrupted) {
		MyShooter.getInstace().setMiddlePower(0);
		MyShooter.getInstace().setUpperPower(0);
	}
}
