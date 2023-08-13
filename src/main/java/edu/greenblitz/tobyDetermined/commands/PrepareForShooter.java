package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.Funnel.RunFunnel;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.greenblitz.tobyDetermined.subsystems.Funnel;
import edu.greenblitz.tobyDetermined.subsystems.ThingsToHandleBalls;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class PrepareForShooter extends GBCommand { //// new!!!!!!!!!!!!
	private ThingsToHandleBalls thingsToHandleBalls;
	private Funnel funnel;
	private IntakeRoller roller;
	
	public PrepareForShooter(){
		funnel = Funnel.getInstance();
		roller = IntakeRoller.getInstance();
		thingsToHandleBalls = ThingsToHandleBalls.getInstance();
		require(funnel);
		require(roller);
		require(thingsToHandleBalls);
		new ParallelDeadlineGroup(//activates both roller and funnel until ball is no longer at macro switch (was probably propelled)
				new WaitUntilCommand(() -> !thingsToHandleBalls.getSwitch()),
				new RunFunnel(),
				new RunRoller()
		);
	}
}