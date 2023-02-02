package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class ExtendToLength extends ExtenderCommand {

    private double wantedLength;
    private static final double IN_PLACE_FOR_TIME = 5; //in ticks (1/20 of a second)
    private double inLengthForTimeCNT = 0;

    /**
     * @param length length in meters
     * */
    public ExtendToLength(double length){
        this.wantedLength = length;
    }

    @Override
    public void initialize() {
        inLengthForTimeCNT = 0;
    }

    @Override
    public void execute() {
        extender.setLength(wantedLength);
    }

    @Override
    public boolean isFinished() {
        if(Extender.getInstance().isAtLength(wantedLength)){ //fixme ? maybe tolerance not good
            inLengthForTimeCNT++;
        }else{
            inLengthForTimeCNT = 0;
        }

        return inLengthForTimeCNT > IN_PLACE_FOR_TIME;
    }

    @Override
    public void end(boolean interrupted) {
        extender.stop();
    }
}
