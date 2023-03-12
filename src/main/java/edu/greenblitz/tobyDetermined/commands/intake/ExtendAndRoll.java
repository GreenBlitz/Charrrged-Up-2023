package edu.greenblitz.tobyDetermined.commands.intake;

import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ExtendAndRoll extends SequentialCommandGroup {

	public ExtendAndRoll(){
		super(new ExtendRoller(),
				new RunRoller());
	}
}
