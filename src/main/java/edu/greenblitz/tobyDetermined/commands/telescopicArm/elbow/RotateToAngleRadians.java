package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotateToAngleRadians extends ElbowCommand {

    private double wantedAngle;

    public RotateToAngleRadians(double angle){
        wantedAngle = angle;
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
        return elbow.isAtAngle();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        elbow.stop();
        SmartDashboard.putBoolean("is at angle?", true);
    }
}
