package edu.greenblitz.tobyDetermined.commands.Funnel;

import edu.greenblitz.tobyDetermined.subsystems.Funnel;
import edu.greenblitz.utils.GBCommand;

public class RunFunnel extends GBCommand {

    public RunFunnel(){
        require(Funnel.getInstance());
    }

    @Override
    public void execute() {
        if(Funnel.getInstance().isInverted()) {
            Funnel.getInstance().setPower(0.6);
        }

    }

    @Override
    public void end(boolean interrupted) {
        Funnel.getInstance().setPower(0);
    }
}
