package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.greenblitz.utils.GBCommand;

public abstract class ExtenderCommand extends GBCommand {

    protected Extender extender;

    public ExtenderCommand (){
        extender = Extender.getInstance();
        require(extender);
    }


}
