package edu.greenblitz.tobyDetermined.commands.SystemCheck;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

import java.util.function.BooleanSupplier;

public class FullIntake extends ConditionalCommand {
	public FullIntake() {
		super(new FullIntakeCube(), new FullIntakeCone(), ObjectSelector::IsCube);
	}
}
