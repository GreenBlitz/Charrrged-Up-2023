package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.core.appender.rolling.action.IfNot;
import org.littletonrobotics.junction.Logger;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Extender.Simulation.SIM_PID;

public class SimulationExtender implements IExtender {
    ElevatorSim extenderSim;
    private double appliedVoltage;

    public SimulationExtender() {
        this.extenderSim = new ElevatorSim(
                DCMotor.getNEO(RobotMap.TelescopicArm.Extender.Simulation.MotorSimulationConstants.NUMBER_OF_MOTORS),
                RobotMap.TelescopicArm.Extender.Simulation.MotorSimulationConstants.GEAR_RATIO,
                RobotMap.TelescopicArm.Extender.Simulation.MotorSimulationConstants.CARRIAGE_MASS,
                RobotMap.TelescopicArm.Extender.EXTENDER_EXTENDING_GEAR_RADIUS,
                RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT,
                RobotMap.TelescopicArm.Extender.EXTENDED_LENGTH,
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
        SmartDashboard.putNumber("simVoltage",appliedVoltage);
        extenderSim.setInputVoltage(appliedVoltage);
    }


    @Override
    public void updateInputs(ExtenderInputs inputs) {
        extenderSim.update(RobotMap.SimulationConstants.TIME_STEP);

        inputs.appliedOutput = appliedVoltage;
        inputs.outputCurrent = extenderSim.getCurrentDrawAmps();
        inputs.position = extenderSim.getPositionMeters();
        inputs.velocity = extenderSim.getVelocityMetersPerSecond();
        inputs.reverseLimitSwitchPressed = extenderSim.hasHitLowerLimit();

        inputs.tolerance = RobotMap.TelescopicArm.Extender.Simulation.SIM_LENGTH_TOLERANCE;

        inputs.kP = RobotMap.TelescopicArm.Extender.Simulation.SIM_PID.getKp();
        inputs.kI = RobotMap.TelescopicArm.Extender.Simulation.SIM_PID.getKi();
        inputs.kD = RobotMap.TelescopicArm.Extender.Simulation.SIM_PID.getKd();
    }

    @Override
    public void setPosition(double position) {
//        System.out.println("[Extender]: tried setting the position to " + position);
        Logger.getInstance().recordOutput("Arm/Extender", "tried setting the position to " + position);
    }


    @Override
    public void setIdleMode(CANSparkMax.IdleMode idleMode) {
        Logger.getInstance().recordOutput("Arm/Extender", "tried setting the idleMode to " + idleMode.name());

    }

    @Override
    public void enableSoftLimit(CANSparkMax.SoftLimitDirection direction, boolean isEnabled) {
        Logger.getInstance().recordOutput("Arm/Extender", "tried to " + (isEnabled ? "Enable" : "Disable") + " soft limit for direction " + direction.name());
    }

    @Override
    public void enableBackSwitchLimit(boolean enable) {
        Logger.getInstance().recordOutput("Arm/Extender", "tried to " + (enable ? "Enable" : "Disable") + " the back switch limit");
    }


}
