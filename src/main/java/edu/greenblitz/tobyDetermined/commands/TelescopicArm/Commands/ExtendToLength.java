package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands;

import edu.greenblitz.tobyDetermined.commands.TelescopicArm.ExtenderCommand;

public class ExtendToLength extends ExtenderCommand {

    private double wantedLength;
    private static final double tolerance = 0.03;

    private static final double inPlaceTime = 5; //in ticks (1/20 of a second)
    private double inLengthCnt = 0;

    /**
     * @param length length in meters
     * */
    public ExtendToLength(double length){
        this.wantedLength = length;
    }

    @Override
    public void initialize() {
        inLengthCnt = 0;
    }

    @Override
    public void execute() {
        extender.setLength(wantedLength);
    }

    @Override
    public boolean isFinished() {
        if(Math.abs(extender.getLength() - wantedLength) <= tolerance){ //fixme ? maybe tolerance not good
            inLengthCnt++;
        }else{
            inLengthCnt = 0;
        }

        return inLengthCnt > inPlaceTime;
    }

    @Override
    public void end(boolean interrupted) {
        extender.stop();
    }
}
