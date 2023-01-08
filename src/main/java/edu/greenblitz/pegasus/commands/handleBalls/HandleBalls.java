package edu.greenblitz.pegasus.commands.handleBalls;

import edu.greenblitz.pegasus.commands.funnel.ReverseRunFunnel;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.IntakeCommand;
import edu.greenblitz.pegasus.commands.intake.roller.ReverseRunRoller;
import edu.greenblitz.pegasus.commands.shooter.ShooterEvacuate;
import edu.greenblitz.pegasus.subsystems.Funnel;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class HandleBalls extends IntakeCommand {


	/**
	 * this command checks if an enemy ball is trying to enter the Funnel,
	 * and if so it is rejecting it from entering the funnel system using a
	 * color sensor to check the color
	 *
	 * @see com.revrobotics.ColorSensorV3 colorSensor.
	 * @see Indexing Subsystem
	 */

	private final Indexing index;
	private boolean isBallInFunnel;

	public HandleBalls() {
		this.index = Indexing.getInstance();
		isBallInFunnel = false;
		require(Indexing.getInstance());
	}

	@Override
	public void execute() {
		SmartDashboard.putBoolean("isBallInFunnel", isBallInFunnel);
		SmartDashboard.putBoolean("isEnemyBallUnSensor", index.isEnemyBallInSensor());
		SmartDashboard.putBoolean("isMacroSwitch", Funnel.getInstance().isMacroSwitchPressed());

		if (index.isTeamsBallInSensor()) {
			//if our team's ball is in front of the sensor activate the boolean
			isBallInFunnel = true;
		}
		if (Funnel.getInstance().isMacroSwitchPressed()) {
			//if the ball got to the macroSwitch then disable the boolean
			isBallInFunnel = false;
		}

		if (index.isEnemyBallInSensor()) {
			if (Funnel.getInstance().isMacroSwitchPressed() || isBallInFunnel) {
				isBallInFunnel = false;
				//back direction
				new ParallelDeadlineGroup(
						new WaitCommand(0.5),
						new ReverseRunRoller(),
						new ReverseRunFunnel().raceWith(new WaitCommand(0.2))
				).andThen(new RunFunnel().until(() -> Funnel.getInstance().isMacroSwitchPressed())).schedule(false);
			} else {
				//shooter evacuation
				new ShooterEvacuate().raceWith(new WaitCommand(5)).schedule(false);

			}
		}
	}
}
