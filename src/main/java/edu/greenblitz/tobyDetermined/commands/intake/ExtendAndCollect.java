package edu.greenblitz.tobyDetermined.commands.intake;

import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ExtendAndCollect extends ParallelCommandGroup {
	public ExtendAndCollect() {
		addCommands(new ExtendRoller(), new RunRoller());
	}
}
