package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ExtenerSim;
import edu.greenblitz.utils.GBCommand;

public abstract class AutoExtenderCommand extends GBCommand {

    protected ExtenerSim extender;

    public AutoExtenderCommand (){
        extender = ExtenerSim.getInstance();
        require(extender);
    }


}
