package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetArmVoltage extends ExtenderCommand{

    private double voltage;

    public SetArmVoltage(double voltage){
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
