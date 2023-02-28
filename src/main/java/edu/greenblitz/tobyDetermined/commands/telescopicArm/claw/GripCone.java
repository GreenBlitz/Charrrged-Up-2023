package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.wpilibj.Timer;

public class GripCone extends ClawCommand {
double time;
    public GripCone(double time){
        this.time = time;
    }

    public GripCone(){
        this(0);
    }

    Timer timer;
    @Override
    public void initialize() {
        claw.coneCatchMode();
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
        claw.motorGrip();
    }

    @Override
    public boolean isFinished() {
        if (time > 0) {
            return timer.hasElapsed(time);
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopMotor();
    }
}
