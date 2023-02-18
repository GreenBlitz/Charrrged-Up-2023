package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class GripCone extends ClawCommand {


    Timer timer;
    @Override
    public void initialize() {
        claw.coneCatchMode();
        claw.motorGrip();
        timer = new Timer();
        timer.start();

    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(RobotMap.TelescopicArm.Claw.TIME_OF_GRIP_CONSTANT);
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopMotor();
    }
}
