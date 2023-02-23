package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class EjectFromClaw extends ClawCommand {

    Timer timer;
    private double timeOfEjection;
    public EjectFromClaw(double timeOfEjection){

        this.timeOfEjection = timeOfEjection;
    }

    public EjectFromClaw(){
        this(RobotMap.TelescopicArm.Claw.TIME_OF_GRIP_CONSTANT);
    }


    @Override
    public void initialize() {
        claw.cubeCatchMode(); //the wider
        claw.motorEject();
        timer = new Timer();
        timer.start();
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(timeOfEjection);
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopMotor();
    }
}
