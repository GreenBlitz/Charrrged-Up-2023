package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.motors.GBSparkMax;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Extender.EXTENDER_CONFIG_OBJECT;

public class NeoExtender implements IExtender {


    private GBSparkMax motor;

    private boolean doesSensorExists = true;

    public NeoExtender() {
//        motor = new GBSparkMax(RobotMap.TelescopicArm.Extender.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
//        motor.config(RobotMap.TelescopicArm.Extender.EXTENDER_CONFIG_OBJECT);
//        motor.setSmartCurrentLimit(50, EXTENDER_CONFIG_OBJECT.getCurrentLimit());
//        motor.getEncoder().setPosition(RobotMap.TelescopicArm.Extender.STARTING_LENGTH);
//        motor.getReverseLimitSwitch(RobotMap.TelescopicArm.Extender.SWITCH_TYPE).enableLimitSwitch(doesSensorExists);
//        motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
//        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.TelescopicArm.Extender.FORWARD_LIMIT);
//        motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);
//        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, BACKWARDS_LIMIT);
//        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        motor = Extender.getInstance().motor;
    }


    @Override
    public void setPower(double power) {
        motor.set(power);
    }

    @Override
    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }

    @Override
    public void setIdleMode(CANSparkMax.IdleMode idleMode) {
        motor.setIdleMode(idleMode);
    }

    @Override
    public void enableSoftLimit(CANSparkMax.SoftLimitDirection direction, boolean isEnabled) {
        motor.enableSoftLimit(direction, isEnabled);
    }

    @Override
    public void enableBackSwitchLimit(boolean enable) {
        motor.getReverseLimitSwitch(RobotMap.TelescopicArm.Extender.SWITCH_TYPE).enableLimitSwitch(enable);
    }

    @Override
    public void setPosition(double position) {
        motor.getEncoder().setPosition(position);
    }

    @Override
    public void updateInputs(ExtenderInputs inputs) {
        inputs.appliedOutput = motor.getAppliedOutput();
        inputs.outputCurrent = motor.getOutputCurrent();
        inputs.position = motor.getEncoder().getPosition();
        inputs.velocity = motor.getEncoder().getVelocity();
        inputs.kP = motor.getPIDController().getP();
        inputs.kI = motor.getPIDController().getI();
        inputs.kD = motor.getPIDController().getD();
        inputs.reverseLimitSwitchPressed = motor.getReverseLimitSwitch(RobotMap.TelescopicArm.Extender.SWITCH_TYPE).isPressed();
        inputs.tolerance = RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE;

    }
}
