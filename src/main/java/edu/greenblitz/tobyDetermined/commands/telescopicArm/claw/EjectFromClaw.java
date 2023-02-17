package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class EjectFromClaw extends ClawCommand {

    private double timeOfEjection;
    public EjectFromClaw(double timeOfEjection){

        this.timeOfEjection = timeOfEjection;
    }

    public EjectFromClaw(){
        timeOfEjection =  RobotMap.telescopicArm.claw.TIME_OF_GRIP_CONSTANT;
    }


    @Override
    public void initialize() {
        claw.cubeCatchMode(); //the wider
        claw.motorEject();
    }

    @Override
    public boolean isFinished() {
        Timer.delay(timeOfEjection);
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopMotor();
    }
}
