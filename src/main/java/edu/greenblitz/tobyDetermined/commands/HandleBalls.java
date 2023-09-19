package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.commands.Funnel.RunFunnel;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.greenblitz.tobyDetermined.commands.shooter.RunShooterByPower;
import edu.greenblitz.tobyDetermined.subsystems.Shooter;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class HandleBalls extends GBCommand {
    @Override
    public void execute() {
        if (Shooter.getInstance().isBallEnemy()) {
            new ParallelCommandGroup(
                    new RunShooterByPower(0.5),
                    new RunFunnel(),
                    new RunRoller()
            ).raceWith(new WaitCommand(5)).schedule();
        }
    }
}
