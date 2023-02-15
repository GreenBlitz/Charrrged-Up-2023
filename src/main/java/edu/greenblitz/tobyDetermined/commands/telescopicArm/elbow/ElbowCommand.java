package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSim;
import edu.greenblitz.utils.GBCommand;

public abstract class ElbowCommand extends GBCommand {

    protected ElbowSim elbow;

    public ElbowCommand (){
        elbow = ElbowSim.getInstance();
        require(elbow);
    }

}
