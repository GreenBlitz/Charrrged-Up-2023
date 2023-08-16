package edu.greenblitz.tobyDetermined.commands.shooter;

import edu.greenblitz.tobyDetermined.subsystems.Shooter;
import edu.greenblitz.utils.GBCommand;

public class RunShooterByPower extends GBCommand {
    private double power;

    private Shooter shooter = Shooter.getInstance();

    public RunShooterByPower(double power){
        this.power = power;
        //require(Shooter.getInstance());
    }

    @Override
    public void execute() {
        shooter.setPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
}
