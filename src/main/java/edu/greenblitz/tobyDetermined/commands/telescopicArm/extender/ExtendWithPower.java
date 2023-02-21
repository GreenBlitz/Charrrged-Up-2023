package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

public class ExtendWithPower extends ExtenderCommand{
    @Override
    public void execute() {
        extender.setMotorVoltage(3);
    }

    @Override
    public void end(boolean interrupted) {
        extender.setMotorVoltage(0);
    }
}
