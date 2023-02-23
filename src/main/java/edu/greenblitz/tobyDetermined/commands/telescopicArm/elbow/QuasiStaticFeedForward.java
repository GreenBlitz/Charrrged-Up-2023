package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class QuasiStaticFeedForward extends ElbowCommand {
    private double velocity, rampRate, targetVel;

    public QuasiStaticFeedForward(double rampRate){
        velocity = 0;
        this.rampRate = rampRate;
        SmartDashboard.putNumber("elbow targetVel", 0.002);
    }

    @Override
    public void initialize() {
        super.initialize();
        targetVel = SmartDashboard.getNumber("elbow targetVel", 0.002);

    }

    @Override
    public void execute() {
        super.execute();
        velocity += rampRate;
        elbow.setMotorVoltage(velocity);

    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putNumber("arm velocity", elbow.getVelocity());
        return super.isFinished() || Math.abs(elbow.getVelocity()) > targetVel;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        elbow.setMotorVoltage(0);
        SmartDashboard.putNumber("elbow ks+kg", velocity);
        velocity = 0;
    }

}
