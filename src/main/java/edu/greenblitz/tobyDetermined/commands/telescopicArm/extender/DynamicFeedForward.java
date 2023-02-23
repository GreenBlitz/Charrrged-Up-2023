package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

public class DynamicFeedForward extends ExtenderCommand{
    private double voltage, ksAndKg;



    public DynamicFeedForward(double voltage, double ksAndKg){
        this.voltage = voltage;
        this.ksAndKg = ksAndKg;
    }

    @Override
    public void initialize() {
        super.initialize();

    }

    @Override
    public void execute() {
        super.execute();
        SmartDashboard.putNumber("extender velocity", extender.getVelocity());
        extender.setMotorVoltage(voltage);
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || extender.isAtLength(voltage > 0 ? RobotMap.TelescopicArm.Extender.MAX_LENGTH_IN_ROBOT : RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);

        extender.setMotorVoltage(0);
        SmartDashboard.putNumber("extender kv", (Math.abs(voltage) - ksAndKg) / Math.abs(extender.getVelocity()));
    }

}
