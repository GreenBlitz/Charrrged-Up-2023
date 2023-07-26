package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.handFlip;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HandFlipCommand extends GBCommand {

    protected handFlip handFlip1;

    public HandFlipCommand (){
        handFlip1 = handFlip1.getInstance();
        require(handFlip1);
    }

    @Override
    public void execute() {
        handFlip1 = handFlip1.getInstance();
        handFlip1.setSpeed(0.5);

    }


    @Override
    public void end(boolean interrupted){
        handFlip1.getInstance().setSpeed(0);
    }


}
