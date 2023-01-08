package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.pegasus.subsystems.Funnel;
import edu.greenblitz.pegasus.subsystems.Shooter;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class InsertIntoShooter extends SequentialCommandGroup {

	private double startTime;
	private boolean reported = false;

	// AKA InsertoShooter @tal935
	public InsertIntoShooter() {
		addCommands(
				new MoveBallUntilClick(),

				//waits until the shooter is ready
				new WaitUntilCommand(() -> Shooter.getInstance().isPreparedToShoot()),

				new ParallelDeadlineGroup(//activates both roller and funnel until ball is no longer at macro switch (was probably propelled)
						new WaitUntilCommand(() -> !Funnel.getInstance().isMacroSwitchPressed()),
						new RunFunnel(),
						new RunRoller()
				));

	}

	@Override
	public void initialize() {
		super.initialize();
		startTime = System.currentTimeMillis() / 1000.0;
	}


	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		reported = false;
	}
}
