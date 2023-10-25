package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.sensors.CANCoder;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SdsSwerveModule;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.Conversions;
import edu.greenblitz.utils.motors.GBFalcon;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.util.Units;

import static edu.greenblitz.utils.Conversions.MK4IConversions.convertRadsToTicks;

public class MK4iModuleIO implements IModuleIO {

    private GBFalcon angularMotor;
    private GBFalcon linearMotor;
    private CANCoder canCoder;
    private SimpleMotorFeedforward feedforward;
    private double encoderOffset;


    public MK4iModuleIO(SwerveChassis.Module module) {

        SdsSwerveModule.SdsSwerveModuleConfigObject configObject;
        switch (module) {

            case FRONT_LEFT:
                configObject = RobotMap.Swerve.SdsModuleFrontLeft;
                break;
            case FRONT_RIGHT:
                configObject = RobotMap.Swerve.SdsModuleFrontRight;
                break;
            case BACK_LEFT:
                configObject = RobotMap.Swerve.SdsModuleBackLeft;
                break;
            case BACK_RIGHT:
                configObject = RobotMap.Swerve.SdsModuleBackRight;
                break;
            default:
                throw new IllegalArgumentException("Invalid module");
        }

        angularMotor = new GBFalcon(configObject.angleMotorID);
        angularMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseAngConfObj));

        linearMotor = new GBFalcon(configObject.linearMotorID);
        linearMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseLinConfObj).withInverted(configObject.linInverted));

        canCoder = new CANCoder(configObject.AbsoluteEncoderID);
        this.encoderOffset = configObject.encoderOffset;

//		canCoder.setPositionToAbsolute();
//		canCoder.setPosition(canCoder.getPosition() - Units.rotationsToDegrees(encoderOffset));

        this.feedforward = new SimpleMotorFeedforward(RobotMap.Swerve.SdsSwerve.ks, RobotMap.Swerve.SdsSwerve.kv, RobotMap.Swerve.SdsSwerve.ka);
    }


    @Override
    public void setLinearVelocity(double speed) {
        linearMotor.set(
                TalonFXControlMode.Velocity,
                speed / RobotMap.Swerve.SdsSwerve.linTicksToMetersPerSecond,
                DemandType.ArbitraryFeedForward,
                feedforward.calculate(speed) / Battery.getInstance().getCurrentVoltage());

    }

    @Override
    public void rotateToAngle(double angleInRadians) {
        angularMotor.set(ControlMode.Position, Conversions.MK4IConversions.convertRadsToTicks(angleInRadians));
    }

    @Override
    public void setLinearVoltage(double voltage) {
        linearMotor.set(ControlMode.PercentOutput, voltage / Battery.getInstance().getCurrentVoltage());
    }

    @Override
    public void setAngularVoltage(double voltage) {
        angularMotor.set(ControlMode.PercentOutput, voltage / Battery.getInstance().getCurrentVoltage());
    }

    @Override
    public void setLinearIdleModeBrake(boolean isBrake) {
        linearMotor.setNeutralMode(isBrake ? NeutralMode.Brake : NeutralMode.Coast);
    }

    @Override
    public void setAngularIdleModeBrake(boolean isBrake) {
        angularMotor.setNeutralMode(isBrake ? NeutralMode.Brake : NeutralMode.Coast);
    }

    @Override
    public void resetAngle(double angleInRads) {
        angularMotor.setSelectedSensorPosition(Conversions.MK4IConversions.convertRadsToTicks(angleInRads));
    }

    @Override
    public void stop() {
        linearMotor.set(ControlMode.PercentOutput, 0);
        angularMotor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void updateInputs(IModuleIOInputsAutoLogged inputs) {
        inputs.linearVelocity = Conversions.MK4IConversions.convertTicksPer100msToMeterPerSecond(linearMotor.getSelectedSensorVelocity());
        inputs.angularVelocity =  Conversions.MK4IConversions.convertTicksPer100msToRadsPerSecond(angularMotor.getSelectedSensorVelocity());

        inputs.linearVoltage = linearMotor.getMotorOutputVoltage();
        inputs.angularVoltage = angularMotor.getMotorOutputVoltage();

        inputs.linearCurrent = linearMotor.getSupplyCurrent();
        inputs.angularCurrent = angularMotor.getStatorCurrent();

        inputs.linearMetersPassed = Conversions.MK4IConversions.convertTicksToMeters(linearMotor.getSelectedSensorPosition());
        inputs.angularPositionInRads = Conversions.MK4IConversions.convertTicksToRads(angularMotor.getSelectedSensorPosition());

        inputs.absoluteEncoderPosition = Units.rotationsToRadians(
                Units.degreesToRotations(canCoder.getAbsolutePosition()) - encoderOffset * RobotMap.Swerve.SdsSwerve.magEncoderTicksToFalconTicks
        );
        inputs.isAbsoluteEncoderConnected = canCoder.getFirmwareVersion() != -1;
    }
}
