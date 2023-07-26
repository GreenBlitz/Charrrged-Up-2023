package edu.greenblitz.tobyDetermined.commands.shooter;

import edu.greenblitz.tobyDetermined.subsystems.Shooter;
import edu.greenblitz.utils.GBCommand;

public class RunShooterByPower extends GBCommand {


    private double power;

    public RunShooterByPower(double power){
        this.power = power;
    }

    @Override
    public void execute() {
        Shooter.getInstance().setPower(this.power);
    }

    @Override
    public void end(boolean interrupted) {
        Shooter.getInstance().stop();
    }
}
