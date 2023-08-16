package edu.greenblitz.tobyDetermined.commands.armarmCom.extenderEXTENDER;

import edu.greenblitz.tobyDetermined.subsystems.armarm.ExtenderSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;

public class ExtenderCommand123 extends GBCommand {

    protected ExtenderSub extender;

    public ExtenderCommand123 (){
        extender = ExtenderSub.getInstance();
        require(extender);
    }
}
