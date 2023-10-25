package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.Conversions;
import edu.greenblitz.utils.motors.GBFalcon;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import org.littletonrobotics.junction.Logger;

public class SimModuleIO implements IModuleIO {

    private final DCMotorSim linearMotor;
    private final DCMotorSim angularMotor;
    private IModuleIOInputsAutoLogged lastInputs = new IModuleIOInputsAutoLogged();
    private final SwerveChassis.Module module;
    private final PIDController angularController = new PIDController(
            RobotMap.Swerve.SimulationSwerve.angularController.kP,
            RobotMap.Swerve.SimulationSwerve.angularController.kI,
            RobotMap.Swerve.SimulationSwerve.angularController.kD
    );

    private double angularAppliedVoltage, linearAppliedVoltage;

    public SimModuleIO(SwerveChassis.Module module) {
        this.module = module;
        System.out.println("[Init] simulation swerve module " + this.module.name());

        this.linearMotor = new DCMotorSim(
                DCMotor.getFalcon500(1),
                RobotMap.Swerve.SdsSwerve.LIN_GEAR_RATIO,
                0.01
                );
        this.angularMotor = new DCMotorSim(
                DCMotor.getFalcon500(1),
                RobotMap.Swerve.SdsSwerve.ANG_GEAR_RATIO,
                0.0001

        );
    }


    @Override
    public void setLinearVelocity(double speed) {
        final double power = speed / RobotMap.Swerve.MAX_VELOCITY;
        final double voltage = power * 12;
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
    public void updateInputs(IModuleIOInputsAutoLogged inputs) {
        linearMotor.update(RobotMap.SimulationConstants.TIME_STEP);
        angularMotor.update(RobotMap.SimulationConstants.TIME_STEP);

        inputs.linearVelocity = Conversions.MK4IConversions.convertRPMToMeterPerSecond(linearMotor.getAngularVelocityRPM()); // [m/s]
        inputs.angularVelocity = angularMotor.getAngularVelocityRadPerSec(); // [Rad/s]

        inputs.linearVoltage = linearAppliedVoltage;
        inputs.angularVoltage = angularAppliedVoltage;

        inputs.linearCurrent = linearMotor.getCurrentDrawAmps();
        inputs.angularCurrent = angularMotor.getCurrentDrawAmps();

        inputs.linearMetersPassed = Conversions.MK4IConversions.convertTicksToMeters(linearMotor.getAngularPositionRotations() * 4096);
        inputs.angularPositionInRads = angularMotor.getAngularPositionRad();

        inputs.absoluteEncoderPosition = inputs.angularPositionInRads;
        inputs.isAbsoluteEncoderConnected = true;

        lastInputs = inputs;
    }
}
