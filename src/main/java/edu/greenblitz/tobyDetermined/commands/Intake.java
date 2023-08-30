package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.utils.GBCommand;

public class Intake extends GBCommand {

	@Override
	public void execute() {
		MyShooter.getInstace().setLowerPower(0.3);
	}

	@Override
	public void end(boolean interrupted) {
		MyShooter.getInstace().setLowerPower(0);
	}
}
