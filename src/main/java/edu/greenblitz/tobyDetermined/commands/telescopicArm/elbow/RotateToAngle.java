package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.wpi.first.math.util.Units;

public class RotateToAngle extends ElbowCommand {

    private double wantedAngle;
    private int inAngleCNT; //a counter for the time that the arm is in the right angle
    private static final int IN_ANGLE_TIME = 5; //in ticks
    private static final double TOLERANCE = Units.degreesToRadians(3);

    public RotateToAngle (double angle){
        wantedAngle = angle;
    }

    @Override
    public void initialize() {
        inAngleCNT = 0;
    }

    @Override
    public void execute() {
        elbow.setAngle(wantedAngle);
    }

    @Override
    public boolean isFinished() {
        if(Elbow.getInstance().isAtAngle(wantedAngle)){
            inAngleCNT++;
        }else{
            inAngleCNT = 0;
        }

        return inAngleCNT > IN_ANGLE_TIME;
    }

    @Override
    public void end(boolean interrupted) {
        elbow.stop();
    }
}
