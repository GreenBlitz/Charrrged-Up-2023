package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.Shooter;
import edu.greenblitz.utils.GBCommand;

public class ShooterCommand extends GBCommand {
    public ShooterCommand(){
        require(Shooter.getInstance());
    }

    @Override
    public void execute() {
        Shooter.getInstance().SetPower(0.3);
    }

    @Override
    public void end(boolean interrupted) {
        Shooter.getInstance().SetPower(0);
    }
}
