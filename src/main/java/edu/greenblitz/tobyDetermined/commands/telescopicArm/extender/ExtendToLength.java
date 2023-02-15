package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;
public class ExtendToLength extends AutoExtenderCommand {

    private double wantedLength;

    /**
     * @param length length in meters
     * */
    public ExtendToLength(double length){
        this.wantedLength = length;
    }
    @Override
    public void execute() {
        extender.moveTowardsLength(wantedLength);
    }

    @Override
    public boolean isFinished() {
        return extender.isAtLength(wantedLength);
    }

    @Override
    public void end(boolean interrupted) {
        extender.stop();
    }
}
