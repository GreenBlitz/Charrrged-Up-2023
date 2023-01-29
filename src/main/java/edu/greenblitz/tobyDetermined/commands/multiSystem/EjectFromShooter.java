package edu.greenblitz.tobyDetermined.commands.multiSystem;

import edu.greenblitz.tobyDetermined.commands.funnel.ReverseRunFunnel;
import edu.greenblitz.tobyDetermined.commands.intake.roller.ReverseRunRoller;
import edu.greenblitz.tobyDetermined.subsystems.Funnel;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class EjectFromShooter extends ParallelRaceGroup {//todo delete
	public EjectFromShooter() {
		addCommands(
				new WaitUntilCommand(() -> Funnel.getInstance().isMacroSwitchPressed()),
				new ReverseRunFunnel(),
				new ReverseRunRoller()
		);
	}
}
