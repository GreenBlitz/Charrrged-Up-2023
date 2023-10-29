package edu.greenblitz.tobyDetermined.subsystems.swerve.ModuleIOs;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.inputs.SwerveModuleInputsAutoLogged;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.Conversions;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class SimulationSwerveModule implements ISwerveModule {

    private final DCMotorSim linearMotor;
    private final DCMotorSim angularMotor;
    private SwerveModuleInputsAutoLogged lastInputs = new SwerveModuleInputsAutoLogged();
    private final SwerveChassis.Module module;
    private final PIDController angularController = new PIDController(
            RobotMap.Swerve.SimulationSwerve.angularController.kP,
            RobotMap.Swerve.SimulationSwerve.angularController.kI,
            RobotMap.Swerve.SimulationSwerve.angularController.kD
    );

    private double angularAppliedVoltage, linearAppliedVoltage;

    public SimulationSwerveModule(SwerveChassis.Module module) {
        this.module = module;

        this.linearMotor = new DCMotorSim(
                DCMotor.getFalcon500(RobotMap.Swerve.Simulation.SIMULATION_LINEAR_MOTOR.NUMBER_OF_MOTORS),
                RobotMap.Swerve.Simulation.SIMULATION_LINEAR_MOTOR.GEAR_RATIO,
                RobotMap.Swerve.Simulation.SIMULATION_LINEAR_MOTOR.MOMENT_OF_INERTIA
                );
        this.angularMotor = new DCMotorSim(
                DCMotor.getFalcon500(RobotMap.Swerve.Simulation.SIMULATION_ANGULAR_MOTOR.NUMBER_OF_MOTORS),
                RobotMap.Swerve.Simulation.SIMULATION_ANGULAR_MOTOR.GEAR_RATIO,
                RobotMap.Swerve.Simulation.SIMULATION_ANGULAR_MOTOR.MOMENT_OF_INERTIA
        );
    }


    @Override
    public void setLinearVelocity(double speed) {
        final double power = speed / RobotMap.Swerve.MAX_VELOCITY;
        final double voltage = power * RobotMap.SimulationConstants.BATTERY_VOLTAGE;
        setLinearVoltage(voltage);
    }

    @Override
    public void rotateToAngle(double angleInRadians) {
        double diff = Math.IEEEremainder(angleInRadians - lastInputs.angularPositionInRads, 2 * Math.PI);
        diff -= diff > Math.PI ? 2 * Math.PI : 0;
        angleInRadians = lastInputs.angularPositionInRads + diff;

        angularController.setSetpoint(angleInRadians);
        final double voltage = angularController.calculate(lastInputs.angularPositionInRads);
        setAngularVoltage(voltage);
    }

    @Override
    public void setLinearVoltage(double voltage) {
        linearAppliedVoltage = voltage;
        linearMotor.setInputVoltage(voltage);
    }

    @Override
    public void setAngularVoltage(double voltage) {
        angularAppliedVoltage = voltage;
        angularMotor.setInputVoltage(voltage);
    }
    @Override
    public void stop() {
        setLinearVoltage(0);
        setAngularVoltage(0);
    }

    @Override
    public void updateInputs(SwerveModuleInputsAutoLogged inputs) {
        linearMotor.update(RobotMap.SimulationConstants.TIME_STEP);
        angularMotor.update(RobotMap.SimulationConstants.TIME_STEP);

        inputs.linearVelocity = Conversions.MK4IConversions.convertRPMToMeterPerSecond(linearMotor.getAngularVelocityRPM()); // [m/s]
        inputs.angularVelocity = angularMotor.getAngularVelocityRadPerSec(); // [Rad/s]

        inputs.linearVoltage = linearAppliedVoltage;
        inputs.angularVoltage = angularAppliedVoltage;

        inputs.linearCurrent = linearMotor.getCurrentDrawAmps();
        inputs.angularCurrent = angularMotor.getCurrentDrawAmps();

        inputs.linearMetersPassed = Conversions.MK4IConversions.convertTicksToMeters(linearMotor.getAngularPositionRotations() * RobotMap.General.Motors.FALCON_TICKS_PER_ROTATION);
        inputs.angularPositionInRads = angularMotor.getAngularPositionRad();

        inputs.absoluteEncoderPosition = inputs.angularPositionInRads;
        inputs.isAbsoluteEncoderConnected = true;

        lastInputs = inputs;
    }


    @Override
    public void setLinearIdleModeBrake(boolean isBrake) {

    }

    @Override
    public void setAngularIdleModeBrake(boolean isBrake) {

    }

    @Override
    public void resetAngle(double angleInRads) {

    }

}
