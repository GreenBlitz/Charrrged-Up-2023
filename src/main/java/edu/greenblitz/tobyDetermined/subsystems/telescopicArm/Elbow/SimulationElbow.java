package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.Simulation.SIM_PID;

public class SimulationElbow implements IElbow {
    SingleJointedArmSim elbowSim;
    private double appliedVoltage;

    public SimulationElbow() {
        elbowSim = new SingleJointedArmSim(
                DCMotor.getNEO(RobotMap.TelescopicArm.Elbow.Simulation.MotorSimulationConstants.NUMBER_OF_MOTORS),
                RobotMap.TelescopicArm.Elbow.Simulation.MotorSimulationConstants.GEAR_RATIO,
                SingleJointedArmSim.estimateMOI( RobotMap.TelescopicArm.Extender.EXTENDED_LENGTH, 6), //-> 1.0 / 3.0 * mass * length^2,
                RobotMap.TelescopicArm.Extender.EXTENDED_LENGTH,
                RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT,
                RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT,
                false
        );
    }



    @Override
    public void setPower(double power) {
        setVoltage(power * RobotMap.SimulationConstants.BATTERY_VOLTAGE);
    }



    @Override
    public void setVoltage(double voltage) {
        appliedVoltage = MathUtil.clamp(voltage, -RobotMap.SimulationConstants.MAX_MOTOR_VOLTAGE, RobotMap.SimulationConstants.MAX_MOTOR_VOLTAGE);
        elbowSim.setInputVoltage(appliedVoltage);
    }



    @Override
    public void updateInputs(ElbowInputsAutoLogged inputs) {

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

    @Override
    public void setIdleMode(CANSparkMax.IdleMode idleMode) {
    }

    @Override
    public void setSoftLimit(CANSparkMax.SoftLimitDirection direction, double limit) {
    }

    @Override
    public void setAngleRadiansByPID(double goalAngle, double feedForward) {
    }

    @Override
    public void setPosition(double position) {
    }
}
