package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.wpilibj.Timer;

public class GripCube extends ClawCommand {
    @Override
    public void initialize() {
        super.initialize();
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
        super.end(interrupted);
        claw.stopMotor();
    }

}
