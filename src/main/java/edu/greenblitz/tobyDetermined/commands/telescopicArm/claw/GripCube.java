package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class GripCube extends ClawCommand {

    @Override
    public void initialize() {
        claw.cubeCatchMode();
        claw.motorGrip();
    }

    @Override
    public boolean isFinished() {
        Timer.delay(RobotMap.telescopicArm.claw.TIME_OF_GRIP_CONSTANT);
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopMotor();
    }

}
