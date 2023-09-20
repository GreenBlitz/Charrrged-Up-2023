package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.utils.GBCommand;

public class MoveAll extends GBCommand {

	@Override
	public void execute() {
		MyShooter.getInstance().setMiddlePower(0.3);

		MyShooter.getInstance().setUpperPower(0.3);

		MyShooter.getInstance().setLowerPower(0.3);
		
	}

	@Override
	public void end(boolean interrupted) {
		MyShooter.getInstance().setUpperPower(0);
		MyShooter.getInstance().setMiddlePower(0);
		MyShooter.getInstance().setLowerPower(0);

	}
}
