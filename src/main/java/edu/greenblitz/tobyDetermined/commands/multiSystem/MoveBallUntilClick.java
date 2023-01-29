package edu.greenblitz.tobyDetermined.commands.multiSystem;

import edu.greenblitz.tobyDetermined.commands.funnel.RunFunnel;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.greenblitz.tobyDetermined.subsystems.Funnel;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class MoveBallUntilClick extends ParallelDeadlineGroup {

	public MoveBallUntilClick() {
		super(new WaitUntilCommand(() -> Funnel.getInstance().isMacroSwitchPressed()), new RunRoller(),new RunFunnel());
		}

}