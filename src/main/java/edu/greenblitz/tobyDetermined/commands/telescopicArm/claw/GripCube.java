package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.wpilibj.Timer;

public class GripCube extends ClawCommand {
    double time;
    public GripCube(double time){
        this.time = time;
    }

    public GripCube(){}

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
        if(time > 0){
                return timer.hasElapsed(time);
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopMotor();
    }

}
