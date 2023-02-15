
package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

public class RotateToAngle extends ElbowCommand {

    private double wantedAngle;

    public RotateToAngle (double angle){
        wantedAngle = angle;
    }


    @Override
    public void execute() {
        elbow.moveTowardsAngle(wantedAngle);
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
