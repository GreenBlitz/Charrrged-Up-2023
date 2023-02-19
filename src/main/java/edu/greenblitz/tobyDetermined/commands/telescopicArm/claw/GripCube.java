package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.wpilibj.Timer;

public class GripCube extends ClawCommand {

    Timer timer;
    @Override
    public void initialize() {
        claw.cubeCatchMode();
        claw.state = Claw.ClawState.CUBE_IN;
        claw.motorGrip();
        timer = new Timer();
        timer.start();
    }

    @Override
    public boolean isFinished() {
        timer.hasElapsed(RobotMap.TelescopicArm.Claw.TIME_OF_GRIP_CONSTANT);
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopMotor();
    }

}