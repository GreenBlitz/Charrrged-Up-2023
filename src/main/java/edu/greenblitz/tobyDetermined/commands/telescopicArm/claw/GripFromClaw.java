package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.wpi.first.wpilibj.Timer;

public class GripFromClaw extends ClawCommand {
    private static final double timeOfGripConstant = 2;
    private double gripTime;

    public GripFromClaw (double gripTime){
        this.gripTime = gripTime;
    }

    public GripFromClaw(){
        gripTime = timeOfGripConstant;
    }

    @Override
    public void initialize() {
        claw.grip();
    }

    @Override
    public boolean isFinished() {
        Timer.delay(gripTime);
        return true;
    }

    @Override
    public void end(boolean interrupted) {

        claw.stopMotor();
    }
}
