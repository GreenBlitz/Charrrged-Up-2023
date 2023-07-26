package edu.greenblitz.tobyDetermined.commands.Funnel;

import edu.greenblitz.tobyDetermined.subsystems.Funnel;
import edu.greenblitz.utils.GBCommand;

public class ReverseRUnFunnel extends GBCommand {


    public ReverseRUnFunnel(){
        require(Funnel.getInstance());
    }

    @Override
    public void execute() {
        Funnel.getInstance().setPower(-0.6);
    }

    @Override
    public void end(boolean interrupted) {
        Funnel.getInstance().setPower(0);
    }
}
