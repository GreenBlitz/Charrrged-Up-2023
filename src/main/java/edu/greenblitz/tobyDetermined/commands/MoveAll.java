package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.utils.GBCommand;

public class MoveAll extends GBCommand {

	@Override
	public void execute() {
		MyShooter.getInstace().setMiddlePower(0.3);

		MyShooter.getInstace().setUpperPower(0.3);

		MyShooter.getInstace().setLowerPower(0.3);
		
	}

	@Override
	public void end(boolean interrupted) {
		MyShooter.getInstace().setUpperPower(0);
		MyShooter.getInstace().setMiddlePower(0);
		MyShooter.getInstace().setLowerPower(0);

	}
}
