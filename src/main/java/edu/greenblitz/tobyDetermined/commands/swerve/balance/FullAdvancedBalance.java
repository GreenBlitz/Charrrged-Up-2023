package edu.greenblitz.tobyDetermined.commands.swerve.balance;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullAdvancedBalance extends SequentialCommandGroup {

	private double phase2speed = -0.1; // negative as phase 2 is in the opposite direction
	private double phase2duration = 0.5;

	public FullAdvancedBalance(boolean forward){
		if (forward){
			phase2speed *= -1;
		}
		addCommands(new AdvancedBalanceOnRamp(forward), new MoveForDuration(phase2duration, phase2speed));
	}

}
