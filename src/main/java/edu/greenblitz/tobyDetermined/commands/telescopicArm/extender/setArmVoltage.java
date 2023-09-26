package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class setArmVoltage extends ExtenderCommand{

    private double voltage;

    public setArmVoltage(double voltage){
        this.voltage = voltage;
    }

    @Override
    public void execute() {
        extender.setMotorVoltage(voltage);

        SmartDashboard.putNumber("aaa", (extender.getVolt() - RobotMap.TelescopicArm.Extender.kS)/extender.getVelocity());
    }

    @Override
    public void end(boolean interrupted) {
        extender.stop();
    }
}
