package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.utils.GBCommand;

public abstract class ClawCommand extends GBCommand {

    protected Claw claw;
    public ClawCommand (){
        claw = Claw.getInstance();
        require(claw);
    }


    @Override
    public void initialize() {
        super.initialize();
        claw.isUsingSubsystem = true;
    }

    @Override
    public void end(boolean interrupted) {
        claw.isUsingSubsystem = false;
    }
}
