package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;


public class Elbow extends GBSubsystem {


    private static Elbow instance;
    public ElbowState state = ElbowState.IN_ROBOT;
    public GBSparkMax motor;
    private double lastSpeed;

    public static Elbow getInstance() {
        if (instance == null) {
            instance = new Elbow();
        }
        return instance;
    }

    private Elbow() {
        motor = new GBSparkMax(RobotMap.telescopicArm.elbow.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        motor.config(new GBSparkMax.SparkMaxConfObject()
                .withPID(RobotMap.telescopicArm.elbow.PID)
                .withPositionConversionFactor(RobotMap.telescopicArm.elbow.CONVERSION_FACTOR)
                .withIdleMode(CANSparkMax.IdleMode.kBrake)
                .withRampRate(RobotMap.telescopicArm.elbow.MOTOR_RAMP_RATE)
                .withCurrentLimit(RobotMap.telescopicArm.elbow.CURRENT_LIMIT)
                .withVoltageComp(RobotMap.General.VOLTAGE_COMP_VAL)
        );
        motor.getEncoder().setPosition(0); //resetPosition
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, RobotMap.telescopicArm.elbow.BACKWARD_ANGLE_LIMIT);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.telescopicArm.elbow.FORWARD_ANGLE_LIMIT);

        lastSpeed = 0;
    }

    public void moveTowardsAngle(double angleInRads) {
        if (Extender.getHypotheticalState(angleInRads) == Extender.ExtenderState.OUT_OF_BOUNDS) {
            stop();
            return;
        }

        if (Extender.getInstance().getState() == Extender.ExtenderState.OPEN && getHypotheticalState(angleInRads) == ElbowState.IN_ROBOT) {
            setAngleByPID(RobotMap.telescopicArm.elbow.ENTRANCE_ANGLE);
        } else {
            setAngleByPID(angleInRads);
        }
    }

    private void setAngleByPID(double goalAngle) {
        ProfiledPIDController pidController = RobotMap.telescopicArm.elbow.PID_CONTROLLER;
        pidController.setGoal(goalAngle);
        double feedForward = getFeedForward(
                pidController.getSetpoint().velocity, (pidController.getSetpoint().velocity - lastSpeed) / RoborioUtils.getCurrentRoborioCycle(),
                Extender.getInstance().getLength(), getAngle()
        );
        motor.getPIDController().setReference(pidController.getSetpoint().velocity, CANSparkMax.ControlType.kVelocity, 0, feedForward);
    }

    public double getAngle() { //todo maybe not good
        return motor.getEncoder().getPosition();
    }

    public ElbowState getState() {
        return state;
    }

    public void stop() {
        motor.set(0);
    }

    @Override
    public void periodic() {
        state = getHypotheticalState(getAngle());
        lastSpeed = getVelocity();
    }

    public double getVelocity (){
        return motor.getEncoder().getVelocity();
    }
    public static ElbowState getHypotheticalState(double angleInRads) {
        if (angleInRads > RobotMap.telescopicArm.elbow.FORWARD_ANGLE_LIMIT || angleInRads < RobotMap.telescopicArm.elbow.BACKWARD_ANGLE_LIMIT) {
            return ElbowState.OUT_OF_BOUNDS;
        }
        if (angleInRads >= RobotMap.telescopicArm.elbow.ENTRANCE_ANGLE) {
            return ElbowState.OUT_ROBOT;
        }
        return ElbowState.IN_ROBOT;

    }

    public boolean isAtAngle(double wantedAngle) {
        return Math.abs(getAngle() - wantedAngle) <= RobotMap.telescopicArm.elbow.ANGLE_TOLERANCE;
    }

    public boolean isInTheSameState(double wantedAng) {
        return getHypotheticalState(getAngle()) == getHypotheticalState(wantedAng) && getHypotheticalState(wantedAng) != ElbowState.OUT_OF_BOUNDS;
    }

    private static double getFeedForward(double wantedAngularSpeed, double wantedAcc, double extenderLength,double elbowAngle) {
        double Kg = (RobotMap.telescopicArm.elbow.MIN_Kg + (((RobotMap.telescopicArm.elbow.MAX_Kg - RobotMap.telescopicArm.elbow.MIN_Kg) * extenderLength)
                / RobotMap.telescopicArm.extender.EXTENDED_LENGTH)) * Math.cos(elbowAngle - RobotMap.telescopicArm.elbow.STARTING_ANGLE_RELATIVE_TO_GROUND);
        return Kg + RobotMap.telescopicArm.elbow.kS * Math.signum(wantedAngularSpeed) + RobotMap.telescopicArm.elbow.kV * wantedAngularSpeed + RobotMap.telescopicArm.elbow.kA * wantedAcc;
    }

    public enum ElbowState {
        IN_ROBOT, OUT_ROBOT, OUT_OF_BOUNDS
    }

}
