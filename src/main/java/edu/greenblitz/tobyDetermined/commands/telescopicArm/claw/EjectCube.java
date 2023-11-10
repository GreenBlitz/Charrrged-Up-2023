package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.wpilibj.Timer;

public class EjectCube extends ClawCommand {

    Timer timer;
    private double timeOfEjection;

    public EjectCube(double timeOfEjection) {

        this.timeOfEjection = timeOfEjection;
    }

    public EjectCube() {
        this(RobotMap.TelescopicArm.Claw.TIME_OF_GRIP_CONSTANT);
    }


    @Override
    public void initialize() {
        super.initialize();
        if (claw.state == Claw.ClawState.CUBE_MODE) {
            claw.cubeCatchMode(); //the wider
            claw.motorEject();
            timer = new Timer();
            timer.start();
        }
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(timeOfEjection);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        claw.stopMotor();
    }
}
