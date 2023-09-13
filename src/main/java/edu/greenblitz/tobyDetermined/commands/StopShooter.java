package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.utils.GBCommand;

public class StopShooter extends GBCommand {

	@Override
	public void execute() {
		MyShooter.getInstace().setUpperPower(0);
		MyShooter.getInstace().setMiddlePower(0);
	}

}