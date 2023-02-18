package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.wpilibj.Timer;

public class GripCubeFromClaw extends ClawCommand {
    private double gripTime;

    public GripCubeFromClaw(double gripTime){
        this.gripTime = gripTime;
    }

    public GripCubeFromClaw(){
        gripTime = RobotMap.telescopicArm.claw.TIME_OF_GRIP_CONSTANT;
    }

    @Override
    public void initialize() {
        claw.cubeCatchMode();
        claw.state = Claw.ClawState.CUBE_IN;
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
