package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotateToAngleRadians extends ElbowCommand {

    private double wantedAngle;
    private boolean stop;

    public RotateToAngleRadians(double angle){
        wantedAngle = angle;
    }

    public RotateToAngleRadians(double angle, boolean stop){
        this(angle);
        this.stop = stop;
    }

    @Override
    public void initialize() {
        super.initialize();
        elbow.moveTowardsAngleRadians(wantedAngle);
    }

    @Override
    public void execute() {
        elbow.moveTowardsAngleRadians(wantedAngle);
        SmartDashboard.putBoolean("is at angle?", false);
    }

    @Override
    public boolean isFinished() {
        if(stop){
            return elbow.isAtAngle() && elbow.isNotMoving();
        }
        return elbow.isAtAngle();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        elbow.stop();
        SmartDashboard.putBoolean("is at angle?", true);
    }
}
