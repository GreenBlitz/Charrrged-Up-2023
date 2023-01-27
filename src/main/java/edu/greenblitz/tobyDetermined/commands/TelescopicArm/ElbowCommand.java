package edu.greenblitz.tobyDetermined.commands.TelescopicArm;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.utils.GBCommand;

public class ElbowCommand extends GBCommand {

    protected Elbow elbow;

    public ElbowCommand (){
        elbow = Elbow.getInstance();
        require(elbow);
    }

}
