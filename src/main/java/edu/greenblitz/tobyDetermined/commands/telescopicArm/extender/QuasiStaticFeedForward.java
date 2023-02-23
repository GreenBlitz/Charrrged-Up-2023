package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class QuasiStaticFeedForward extends ExtenderCommand{

    private double velocity, rampRate, targetVel;

    public QuasiStaticFeedForward(double rampRate){
        velocity = 0;
        this.rampRate = rampRate;
        SmartDashboard.putNumber("extender targetVel", 0.002);
    }

    @Override
    public void initialize() {
        super.initialize();
        targetVel = SmartDashboard.getNumber("extender targetVel", 0.002);

    }

    @Override
    public void execute() {
        super.execute();
        velocity += rampRate;
        extender.setMotorVoltage(velocity);

    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putNumber("arm velocity", extender.getVelocity());
        return super.isFinished() || Math.abs(extender.getVelocity()) > targetVel;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        extender.setMotorVoltage(0);
        SmartDashboard.putNumber("extender ks+kg", velocity);
        velocity = 0;
    }
}
