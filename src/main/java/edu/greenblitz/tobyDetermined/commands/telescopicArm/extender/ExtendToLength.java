package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ExtendToLength extends ExtenderCommand {

    private double wantedLength;
    private boolean stop;
    /**
     * @param length length in meters
     * */
    public ExtendToLength(double length){
        this.wantedLength = length;
    }

    public ExtendToLength(double length, boolean stop){
        this(length);
        this.stop = stop;
    }

    @Override
    public void initialize() {
        super.initialize();
        extender.moveTowardsLength(wantedLength);

    }

    @Override
    public void execute() {
        extender.moveTowardsLength(wantedLength);
        SmartDashboard.putBoolean("reached length?", false);
    }

    @Override
    public boolean isFinished() {
        if (stop) {
            return extender.isAtLength() && extender.isNotMoving();
        }
        return extender.isAtLength();
    }

    @Override
    public void end(boolean interrupted) {
        extender.stop();
        SmartDashboard.putBoolean("reached length?", true);
    }
}
