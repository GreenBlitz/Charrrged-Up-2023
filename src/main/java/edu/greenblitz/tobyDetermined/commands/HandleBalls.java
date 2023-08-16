package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.commands.Funnel.ReverseRUnFunnel;
import edu.greenblitz.tobyDetermined.commands.Funnel.RunFunnel;
import edu.greenblitz.tobyDetermined.commands.intake.roller.ReverseRunRoller;
import edu.greenblitz.tobyDetermined.commands.shooter.RunShooterByPower;
import edu.greenblitz.tobyDetermined.subsystems.Funnel;
import edu.greenblitz.tobyDetermined.subsystems.Indexing;
import edu.greenblitz.tobyDetermined.subsystems.Shooter;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class HandleBalls extends GBCommand {

	private Indexing indexing;

	public HandleBalls()
	{
		require(Indexing.getInstance());
		this.indexing = Indexing.getInstance();
//		require(Shooter.getInstance());
//		require(Funnel.getInstance());
//		require(IntakeRoller.getInstance());
	}

	public void execute() {
		SmartDashboard.putNumber("red",indexing.getRed());
		SmartDashboard.putNumber("green",indexing.getGreen());
		SmartDashboard.putNumber("blue",indexing.getBlue());

		SmartDashboard.putBoolean("enemyBallIn", indexing.isEnemyBallIn());

		if (indexing.isEnemyBallIn() && indexing.switchOn()) {
			//System.out.println("reverse");
			new ParallelDeadlineGroup(
					new WaitCommand(1.5),
					new ReverseRUnFunnel(),
					new ReverseRunRoller()
			).schedule();
			SmartDashboard.putBoolean("shoot ball", false);
			SmartDashboard.putBoolean("gripper evac", true);


		} else if (indexing.isEnemyBallIn() && !indexing.switchOn()) {
			SmartDashboard.putBoolean("shoot ball", true);
			SmartDashboard.putBoolean("gripper evac", false);

			IntakeRoller.getInstance().roll(0.6);
			new SequentialCommandGroup(
					new ParallelDeadlineGroup(
							new WaitCommand(0.5),
							new InstantCommand(()->Funnel.getInstance().setPower(0.4)).until(()->Indexing.getInstance().switchOn())
					).andThen((new InstantCommand(()->{
						Funnel.getInstance().setPower(0);
						IntakeRoller.getInstance().roll(0);
					}))),

					new ParallelDeadlineGroup(
							new WaitCommand(2),
							new RunShooterByPower(0.4),
							new WaitCommand(0.5).andThen(new InstantCommand(() -> Funnel.getInstance().setPower(0.6))),
							new WaitCommand(0.9).andThen(new InstantCommand(() -> Funnel.getInstance().setPower(0)))
					)
			).schedule();

		}
	}
}
