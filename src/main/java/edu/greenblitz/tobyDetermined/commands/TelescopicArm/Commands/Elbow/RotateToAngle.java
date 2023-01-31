package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.Elbow;

import edu.greenblitz.tobyDetermined.commands.TelescopicArm.ElbowCommand;
import edu.wpi.first.math.util.Units;

public class RotateToAngle extends ElbowCommand {

    private double wantedAngle;
    private int isOnAnglecnt;
    private static final int inAngleTime = 5; //in ticks
    private static final double tolerance = Units.degreesToRadians(3);

    public RotateToAngle (double angle){
        wantedAngle = angle;
    }

    @Override
    public void initialize() {
        isOnAnglecnt = 0;
    }

    @Override
    public void execute() {
        elbow.setAngle(wantedAngle);
    }

    @Override
    public boolean isFinished() {
        if(Math.abs(elbow.getAngle() - wantedAngle) <= tolerance){ //fixme ? maybe tolerance not good
            isOnAnglecnt++;
        }else{
            isOnAnglecnt = 0;
        }

        return isOnAnglecnt > inAngleTime;
    }

    @Override
    public void end(boolean interrupted) {
        elbow.stop();
    }
}
