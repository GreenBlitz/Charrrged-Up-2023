package edu.greenblitz.tobyDetermined.commands.armarmCom.elbowElbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.utils.GBCommand;

public class ElbowCommandElbow extends GBCommand {

    protected ElbowSub elbow;

    public ElbowCommandElbow (){
        elbow = ElbowSub.getInstance();
        require(elbow);
    }
}
