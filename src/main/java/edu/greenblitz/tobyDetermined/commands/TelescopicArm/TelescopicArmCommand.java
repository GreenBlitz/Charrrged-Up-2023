package edu.greenblitz.tobyDetermined.commands.TelescopicArm;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;

public class TelescopicArmCommand extends GBCommand {
    protected Claw claw;
    protected Elbow elbow;
    protected Extender extender;

    public TelescopicArmCommand (){
        claw = Claw.getInstance();
        elbow = Elbow.getInstance();
        extender = Extender.getInstance();

        require(claw);
        require(elbow);
        require(extender);
    }

}
