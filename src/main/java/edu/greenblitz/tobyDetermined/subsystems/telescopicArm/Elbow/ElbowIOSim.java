package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.SIM_PID;

public class ElbowIOSim implements ElbowIO{
    SingleJointedArmSim elbowSim;
    private double appliedVoltage;

    public ElbowIOSim() {
        elbowSim = new SingleJointedArmSim(
                DCMotor.getNEO(1),
                RobotMap.TelescopicArm.Elbow.RELATIVE_POSITION_CONVERSION_FACTOR,
                0.0001, //-> 1.0 / 3.0 * mass * length^2,
                0.2,
                RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT,
                RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT,
                false
        );
    }

    @Override
    public void setPosition(double position) {
        ElbowIO.super.setPosition(position);
    }

    @Override
    public void setPower(double power) {
        setVoltage(power * 12);
    }
    @Override
    public void setVoltage(double voltage) {
        appliedVoltage = MathUtil.clamp(voltage, -12, 12);
        elbowSim.setInputVoltage(appliedVoltage);
    }

    @Override
    public void updateInputs(ElbowInputs inputs) {

        elbowSim.update(RobotMap.SimulationConstants.TIME_STEP);

        inputs.appliedOutput = appliedVoltage;
        inputs.outputCurrent = elbowSim.getCurrentDrawAmps();
        inputs.position = elbowSim.getAngleRads();
        inputs.velocity = elbowSim.getVelocityRadPerSec();
        inputs.absoluteEncoderPosition = elbowSim.getAngleRads();
        inputs.absoluteEncoderVelocity = elbowSim.getVelocityRadPerSec();

        inputs.hasHitForwardLimit = elbowSim.hasHitLowerLimit();
        inputs.hasHitBackwardsLimit = elbowSim.hasHitLowerLimit();

        inputs.kP = SIM_PID.getKp();
        inputs.kI = SIM_PID.getKi();
        inputs.kD = SIM_PID.getKd();
    }
}
