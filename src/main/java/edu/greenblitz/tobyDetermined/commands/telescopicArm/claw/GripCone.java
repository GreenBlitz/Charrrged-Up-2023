package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ClawCommand;
import edu.wpi.first.wpilibj.Timer;

public class GripCone extends ClawCommand {

    @Override
    public void initialize() {
        claw.coneCatchMode();
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
