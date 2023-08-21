package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.commands.Funnel.RunFunnel;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.greenblitz.tobyDetermined.subsystems.Funnel;
import edu.greenblitz.tobyDetermined.subsystems.Indexing;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import scala.concurrent.duration.Deadline;

public class GrippBall extends GBCommand {

	public GrippBall(){
		require(IntakeRoller.getInstance());
		require(Funnel.getInstance());
	}

	@Override
	public void execute() {
		SmartDashboard.putNumber("red",Indexing.getInstance().getRed());
		SmartDashboard.putNumber("green",Indexing.getInstance().getGreen());
		SmartDashboard.putNumber("blue",Indexing.getInstance().getBlue());
		SmartDashboard.putBoolean("ballIn", Indexing.getInstance().ballIn());
		//new RunRoller().schedule();
		IntakeRoller.getInstance().roll(0.4);
	}
	@Override
	public boolean isFinished() {
		return Indexing.getInstance().ballIn();
	}

	@Override
	public void end(boolean interrupted) {

		IntakeRoller.getInstance().roll(0);
		new HandleBalls().schedule();


//		IntakeRoller.getInstance().roll(0);
//		new ParallelDeadlineGroup(
//				new WaitCommand(0.7),
//				new InstantCommand(()->Funnel.getInstance().setPower(0.4)).until(()->Indexing.getInstance().ballIn())
//		).andThen(new HandleBalls());
//		//.andThen((new InstantCommand(()->Funnel.getInstance().setPower(0)))).schedule();
	}
}
