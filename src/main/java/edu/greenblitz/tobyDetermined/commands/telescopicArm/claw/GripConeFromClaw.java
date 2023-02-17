package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.wpilibj.Timer;

public class GripConeFromClaw extends ClawCommand {
    private double gripTime;

    public GripConeFromClaw(double gripTime){
        this.gripTime = gripTime;
    }

    public GripConeFromClaw(){
        gripTime =  RobotMap.telescopicArm.claw.TIME_OF_GRIP_CONSTANT;
    }

    @Override
    public void initialize() {
        claw.coneCatchMode();
        claw.state = Claw.ClawState.CONE_IN;
        claw.motorGrip();
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
