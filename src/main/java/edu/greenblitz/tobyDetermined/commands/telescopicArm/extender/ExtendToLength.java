package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.subsystems.Console;

public class ExtendToLength extends ExtenderCommand {

    private double legalGoalLength;
    private double wantedLength;
//    private ProfiledPIDController pidController;
    public ExtendToLength(double length){
        wantedLength = length;
    }


    @Override
    public void initialize() {
        super.initialize();
//        pidController = new ProfiledPIDController(PID.getKp(), PID.getKi(), PID.getKd(), CONSTRAINTS);
        legalGoalLength = extender.getLegalGoalLength(wantedLength);
//        pidController.reset(new TrapezoidProfile.State(extender.getLength(), extender.getVelocity()));
        Console.log("legal length", Double.toString(legalGoalLength));

    }

    @Override
    public void execute() {
        legalGoalLength = extender.getLegalGoalLength(wantedLength);
//        pidController.setGoal(legalGoalLength);
//        SmartDashboard.putNumber("length", extender.getLength());
//        SmartDashboard.putNumber("goal", pidController.getSetpoint().position);
//
//        double pidGain = pidController.calculate(extender.getLength(), legalGoalLength);
//        double feedForward = Extender.getStaticFeedForward( Elbow.getInstance().getAngleRadians()) + Math.signum(pidGain) * kS;
//
//        SmartDashboard.putNumber("pid gain", pidGain);
//        SmartDashboard.putNumber("ff", feedForward);
//        SmartDashboard.putNumber("ks", Math.signum(pidGain) * kS);
//        SmartDashboard.putNumber("output", feedForward + pidGain);

//        extender.debugSetPower(feedForward / Battery.getInstance().getCurrentVoltage() + pidGain);
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
