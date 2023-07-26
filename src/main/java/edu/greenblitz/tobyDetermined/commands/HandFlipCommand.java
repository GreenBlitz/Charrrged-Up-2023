package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.Colors;
import edu.greenblitz.tobyDetermined.subsystems.Indexinggggg;
import edu.greenblitz.tobyDetermined.subsystems.handFlip;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HandFlipCommand extends GBCommand {

    protected Indexinggggg handFlip1;
    public HandFlipCommand (){
        handFlip1 = handFlip1.getInstance();
        require(handFlip1);
    }

    @Override
    public void execute() {
        switch (handFlip1.getBallColor()){
            case Red:
                handFlip1.setSpeed(0.5);
                break;
            case Blue:
                handFlip1.setSpeed(-0.5);
                break;
            case Other:
                handFlip1.setSpeed(0);
                break;
        }

    }


    @Override
    public void end(boolean interrupted){
        handFlip1.setSpeed(0);
    }


}
