package edu.greenblitz.tobyDetermined.subsystems.swerve.Modules;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Calibration;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveModuleConfigObject;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Modules.SwerveModuleInputsAutoLogged;
import edu.greenblitz.utils.Conversions;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.AnalogInput;

import static edu.greenblitz.tobyDetermined.RobotMap.General.Motors.NEO_PHYSICAL_TICKS_TO_RADIANS;

public class KazaSwerveModule implements ISwerveModule{

    private double targetAngle;
    private double targetVel;
    private GBSparkMax angleMotor;
    private GBSparkMax linearMotor;
    private AnalogInput lamprey;
    private SimpleMotorFeedforward feedforward;
    SwerveChassis.Module module;

    public KazaSwerveModule (SwerveChassis.Module module){


        int angleMotorID;
        int linearMotorID;
        int lampreyID;
        boolean linInverted;
        this.module = module;

        SwerveModuleConfigObject configObject;

        switch (module){
            case BACK_RIGHT:
                configObject = RobotMap.Swerve.KazaModuleBackRight;
                break;
            case BACK_LEFT:
                configObject = RobotMap.Swerve.KazaModuleBackLeft;
                break;
            case FRONT_RIGHT:
                configObject = RobotMap.Swerve.KazaModuleFrontRight;
                break;
            case FRONT_LEFT:
                configObject = RobotMap.Swerve.KazaModuleFrontLeft;
                break;
            default:
                throw new IllegalArgumentException("Invalid module");
        }

        angleMotorID = configObject.angleMotorID;
        linearMotorID = configObject.linearMotorID;
        lampreyID = configObject.AbsoluteEncoderID;
        linInverted = configObject.linInverted;

        angleMotor = new GBSparkMax(angleMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
        angleMotor.config(RobotMap.Swerve.KazaSwerve.baseAngConfObj);
        angleMotor.getPIDController().setPositionPIDWrappingEnabled(true);
        angleMotor.getPIDController().setPositionPIDWrappingMaxInput(2* Math.PI);
        angleMotor.getPIDController().setPositionPIDWrappingMinInput(0);

        linearMotor = new GBSparkMax(linearMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
        linearMotor.config(RobotMap.Swerve.KazaSwerve.baseLinConfObj.withInverted(linInverted));

        lamprey = new AnalogInput(lampreyID);
        lamprey.setAverageBits(RobotMap.Swerve.Frankenstein.LAMPREY_AVERAGE_BITS);
        this.feedforward = new SimpleMotorFeedforward(RobotMap.Swerve.KazaSwerve.ks, RobotMap.Swerve.KazaSwerve.kv, RobotMap.Swerve.KazaSwerve.ka);


    }


    @Override
    public void setLinearVelocity(double speed) {
        linearMotor.getPIDController().setReference(speed, CANSparkMax.ControlType.kVelocity);
    }

    @Override
    public void rotateToAngle(double angleInRadians) {
        angleMotor.getPIDController().setReference(angleInRadians, CANSparkMax.ControlType.kPosition);
        targetAngle = angleInRadians;
    }

    @Override
    public void setLinearVoltage(double voltage) {
        linearMotor.setVoltage(voltage);
    }

    @Override
    public void setAngularVoltage(double voltage) {
        angleMotor.setVoltage(voltage);
    }

    @Override
    public void setLinearIdleModeBrake(boolean isBrake) {
        linearMotor.setIdleMode(isBrake ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast);
    }

    @Override
    public void setAngularIdleModeBrake(boolean isBrake) {
        angleMotor.setIdleMode(isBrake ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast);
    }

    @Override
    public void resetAngle(double angleInRads) {
        angleMotor.getEncoder().setPosition(angleInRads);
    }

    @Override
    public void stop() {
        setLinearVoltage(0);
        setAngularVoltage(0);
    }

    @Override
    public void updateInputs(SwerveModuleInputsAutoLogged inputs) {
        inputs.absoluteEncoderPosition = Calibration.CALIBRATION_DATASETS.get(module).get(lamprey.getVoltage()) * NEO_PHYSICAL_TICKS_TO_RADIANS/ RobotMap.Swerve.KazaSwerve.ANG_GEAR_RATIO;
        inputs.isAbsoluteEncoderConnected = lamprey.getVoltage() != 0; //analog input returns 0;

        inputs.linearCurrent = linearMotor.getOutputCurrent();
        inputs.angularCurrent = angleMotor.getOutputCurrent();

        inputs.linearVoltage = linearMotor.getAppliedOutput();
        inputs.angularVoltage = angleMotor.getAppliedOutput();

        inputs.linearVelocity = Conversions.convertRPMToRadsPerSec(linearMotor.getEncoder().getVelocity() * RobotMap.Swerve.KazaSwerve.angleTicksToWheelToRPM);
        inputs.angularVelocity = angleMotor.getEncoder().getVelocity() * RobotMap.Swerve.KazaSwerve.angleTicksToRadians;

        inputs.linearMetersPassed = linearMotor.getEncoder().getPosition();
        inputs.angularPositionInRads = angleMotor.getEncoder().getPosition();


    }
}
