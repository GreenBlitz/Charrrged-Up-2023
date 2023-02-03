package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.wpi.first.math.util.Units;

public class RotateToAngle extends ElbowCommand {

    private double wantedAngle;

    public RotateToAngle (double angle){
        wantedAngle = angle;
    }


    @Override
    public void execute() {
        elbow.setAngle(wantedAngle);
    }

    @Override
    public boolean isFinished() {
        return elbow.isAtAngle(wantedAngle);
    }

    @Override
    public void end(boolean interrupted) {
        elbow.stop();
    }
}
