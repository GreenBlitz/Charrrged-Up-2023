package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.subsystems.Funnel;
import edu.wpi.first.wpilibj2.command.*;

public class ShooterEvacuate extends ParallelRaceGroup {
	/** used by the HandleBalls command to make an
	 * enemy ball if there's no ball from above;
	 * @see edu.greenblitz.pegasus.commands.handleBalls.HandleBalls HandleBalls*/
	public ShooterEvacuate(){
		
		addCommands(
				new ShootByConstant(0.3),
				new SequentialCommandGroup(
						new RunFunnel().alongWith(new RunRoller()).until(() -> Funnel.getInstance().isMacroSwitchPressed()),
						new WaitCommand(0.2),
						new ParallelRaceGroup(new RunFunnel(),new WaitCommand(0.3))
				)
		);
	}
}
