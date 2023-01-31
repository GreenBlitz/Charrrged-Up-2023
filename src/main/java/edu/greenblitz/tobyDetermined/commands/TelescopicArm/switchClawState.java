package edu.greenblitz.tobyDetermined.commands.TelescopicArm;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;

public class switchClawState extends ClawCommand{

    public switchClawState(){

    }

    @Override
    public boolean isFinished() {
        if(claw.getState() == Claw.ClawState.CLOSED){
            claw.open();
        }else{
            claw.close();
        }
    }
}
