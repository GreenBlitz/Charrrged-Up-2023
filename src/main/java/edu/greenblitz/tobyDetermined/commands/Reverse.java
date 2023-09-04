package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.utils.GBCommand;

public class Reverse extends GBCommand {
	@Override
	public void execute() {
		MyShooter.getInstace().Vommit *= -1;
	}
}
