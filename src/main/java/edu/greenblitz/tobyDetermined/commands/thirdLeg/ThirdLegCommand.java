package edu.greenblitz.tobyDetermined.commands.thirdLeg;

import edu.greenblitz.tobyDetermined.subsystems.swerve.intake.IntakeExtender;
import edu.greenblitz.utils.GBCommand;

public abstract class ThirdLegCommand extends GBCommand {
	IntakeExtender extender;
	public ThirdLegCommand(){
		extender = IntakeExtender.getInstance();
		require(extender);
	}
}
