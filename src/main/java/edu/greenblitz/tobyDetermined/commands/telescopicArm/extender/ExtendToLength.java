package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.subsystems.Console;

public class ExtendToLength extends ExtenderCommand {

    private double legalGoalLength;
    private double wantedLength;
    public ExtendToLength(double length){
        wantedLength = length;
    }


    @Override
    public void initialize() {
        super.initialize();
        legalGoalLength = extender.getLegalGoalLength(wantedLength);
//        Console.log("legal length", Double.toString(legalGoalLength));

    }

    @Override
    public void execute() {
        legalGoalLength = extender.getLegalGoalLength(wantedLength);
        extender.unsafeSetGoalLengthByPid(legalGoalLength);
    }

    @Override
    public boolean isFinished() {
        return extender.isAtLength(legalGoalLength);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        extender.stop();
    }
}
