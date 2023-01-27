package edu.greenblitz.tobyDetermined.commands.TelescopicArm;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;

public class ExtenderCommand extends GBCommand {

    protected Extender extender;

    public ExtenderCommand (){
        extender = Extender.getInstance();
        require(extender);
    }


}
