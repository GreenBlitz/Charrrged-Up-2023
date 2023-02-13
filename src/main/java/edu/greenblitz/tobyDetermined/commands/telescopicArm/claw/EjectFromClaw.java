package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.wpi.first.wpilibj.Timer;

public class EjectFromClaw extends ClawCommand {

    public static final double timeOfEjectionConstant = 2;
    private double timeOfEjection;
    public EjectFromClaw(double timeOfEjection){

        this.timeOfEjection = timeOfEjection;
    }

    public EjectFromClaw(){
        timeOfEjection = timeOfEjectionConstant;
    }


    @Override
    public void initialize() {
        claw.eject();
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
