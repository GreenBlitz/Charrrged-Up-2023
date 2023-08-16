package edu.greenblitz.tobyDetermined.commands.armarmCom.clawClaw;

import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ClawCommand;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.Timer;

public class GripCubeCube extends ClawClawCommand {

    @Override
    public void initialize() {
        claw.cubeCatchMode();
    }

    @Override
    public void execute() {
        claw.motorGrip();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopMotor();
    }

}
