package edu.greenblitz.tobyDetermined.commands;


import com.google.common.util.concurrent.Uninterruptibles;
import edu.greenblitz.tobyDetermined.subsystems.ThingsToHandleBalls;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class HandleMyballs extends GBCommand {
	private ThingsToHandleBalls thingsToHandleBalls;
	
	public HandleMyballs() {
		this.thingsToHandleBalls= ThingsToHandleBalls.getInstance();
		require(ThingsToHandleBalls.getInstance());
	}
	
	@Override
	public void execute() {
		if (thingsToHandleBalls.IsBallGood() == ThingsToHandleBalls.BallColor.ENEMYALLIANCE){
			if(thingsToHandleBalls.getSwitch()){
				new ParallelDeadlineGroup(
						new WaitCommand(1),
						new EvacuateFromRoller()).withInterruptBehavior(InterruptionBehavior.kCancelIncoming)
						.schedule();			;
			}
			else{
				new ParallelDeadlineGroup(
						new WaitCommand(1.5), // no need cause of change in ShooterEvacuate
						new ShooterrEvacuate().raceWith(new WaitCommand(2))
				).schedule();
			}
		}
		else if (thingsToHandleBalls.IsBallGood() == ThingsToHandleBalls.BallColor.TEAMALLIANCE) {
			if(!thingsToHandleBalls.getSwitch()) {
				new GetBallUp().schedule();
			}
			else{
				new ParallelDeadlineGroup(
						new WaitCommand(0.5),
						new GetBallToLowerPlace()
				).schedule();
			}
		}
	}
}