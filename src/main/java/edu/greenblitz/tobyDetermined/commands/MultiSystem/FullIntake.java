package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.subsystems.armarm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

public class FullIntake extends ConditionalCommand {
	public FullIntake() {
		super(new FullIntakeCube(), new FullIntakeCone(), ObjectSelector::IsCube);
	}
}
