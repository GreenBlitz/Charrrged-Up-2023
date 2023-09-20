package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.utils.GBCommand;

public class StopShooter extends GBCommand {

	@Override
	public void execute() {
		MyShooter.getInstance().setUpperPower(0);
		MyShooter.getInstance().setMiddlePower(0);
	}

}