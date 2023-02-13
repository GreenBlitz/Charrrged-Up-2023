package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.MotorFeedbackSensor;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Elbow extends GBSubsystem {


    private static Elbow instance;
    public ElbowState state = ElbowState.IN_BELLY;
    public GBSparkMax motor;
    private double lastSpeed;

    public static Elbow getInstance() {
        if (instance == null) {
            init();
            SmartDashboard.putBoolean("elbow initialized via getinstance", true);
        }
        return instance;
    }

    public static void init(){
        instance = new Elbow();
    }

    private Elbow() {
        motor = new GBSparkMax(RobotMap.telescopicArm.elbow.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.config(new GBSparkMax.SparkMaxConfObject()
                .withPID(RobotMap.telescopicArm.elbow.PID)
                .withIdleMode(CANSparkMax.IdleMode.kBrake)
                .withRampRate(RobotMap.telescopicArm.elbow.MOTOR_RAMP_RATE)
                .withCurrentLimit(RobotMap.telescopicArm.elbow.CURRENT_LIMIT)
                .withVoltageComp(RobotMap.General.VOLTAGE_COMP_VAL)
        );
        motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).setPositionConversionFactor(RobotMap.telescopicArm.elbow.POSITION_CONVERSION_FACTOR);
        motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).setPositionConversionFactor(RobotMap.telescopicArm.elbow.VELOCITY_CONVERSION_FACTOR);
        motor.getPIDController().setFeedbackDevice(motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle));
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, RobotMap.telescopicArm.elbow.BACKWARD_ANGLE_LIMIT);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.telescopicArm.elbow.FORWARD_ANGLE_LIMIT);

        lastSpeed = 0;
    }

    public void moveTowardsAngle(double angleInRads) {
        // going out of bounds should not be allowed
        if (Elbow.getHypotheticalState(angleInRads) == ElbowState.OUT_OF_BOUNDS){
            stop();
            return;
        }

        //when moving between states the arm always passes through the IN_FRONT_OF_ENTRANCE zone and so length must be short enough
        // if its not short enough the arm will approach the start of the zone
        if(getState() != getHypotheticalState(angleInRads) &&
                Extender.getInstance().getState() != Extender.ExtenderState.IN_WALL_LENGTH){
            setAngleByPID(state == ElbowState.IN_BELLY ? RobotMap.telescopicArm.elbow.STARTING_WALL_ZONE_ANGLE : RobotMap.telescopicArm.elbow.END_WALL_ZONE_ANGLE);
        }else {
            setAngleByPID(angleInRads);
        }
    }

    public void setAngleByPID(double goalAngle) {
        ProfiledPIDController pidController = RobotMap.telescopicArm.elbow.PID_CONTROLLER;
        pidController.reset(getAngle());
        pidController.setGoal(goalAngle);
        double feedForward = getFeedForward(
                pidController.getSetpoint().velocity, (pidController.getSetpoint().velocity - lastSpeed) / RoborioUtils.getCurrentRoborioCycle(),
                Extender.getInstance().getLength(), getAngle()
        );
        motor.getPIDController().setReference(pidController.getSetpoint().velocity, CANSparkMax.ControlType.kVelocity, 0, feedForward);
    }

    public double getAngle() {
        return motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).getPosition();
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
        return motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).getVelocity();
    }
    public static ElbowState getHypotheticalState(double angleInRads) {
        if (angleInRads > RobotMap.telescopicArm.elbow.FORWARD_ANGLE_LIMIT || angleInRads < RobotMap.telescopicArm.elbow.BACKWARD_ANGLE_LIMIT) {
            return ElbowState.OUT_OF_BOUNDS;
        } else if (angleInRads > RobotMap.telescopicArm.elbow.STARTING_WALL_ZONE_ANGLE && angleInRads <RobotMap.telescopicArm.elbow.END_WALL_ZONE_ANGLE) {
            return ElbowState.WALL_ZONE;
        } else if (angleInRads > RobotMap.telescopicArm.elbow.END_WALL_ZONE_ANGLE) {
            return ElbowState.OUT_ROBOT;
        }
        return ElbowState.IN_BELLY;

    }

    public boolean isAtAngle(double wantedAngle) {
        return Math.abs(getAngle() - wantedAngle) <= RobotMap.telescopicArm.elbow.ANGLE_TOLERANCE;
    }

    public boolean isInTheSameState(double wantedAng) {
        return getHypotheticalState(getAngle()) == getHypotheticalState(wantedAng) && getHypotheticalState(wantedAng) != ElbowState.OUT_OF_BOUNDS;
    }

    public static double getFeedForward(double wantedAngularSpeed, double wantedAcc, double extenderLength,double elbowAngle) {
        double Kg = getStaticFeedForward(extenderLength,elbowAngle);
        return Kg + RobotMap.telescopicArm.elbow.kS * Math.signum(wantedAngularSpeed) + RobotMap.telescopicArm.elbow.kV * wantedAngularSpeed + RobotMap.telescopicArm.elbow.kA * wantedAcc;
    }
    public static double getStaticFeedForward(double extenderLength,double elbowAngle) {
        return (RobotMap.telescopicArm.elbow.MIN_Kg + (((RobotMap.telescopicArm.elbow.MAX_Kg - RobotMap.telescopicArm.elbow.MIN_Kg) * extenderLength)
                / RobotMap.telescopicArm.extender.EXTENDED_LENGTH)) * Math.cos(elbowAngle - RobotMap.telescopicArm.elbow.STARTING_ANGLE_RELATIVE_TO_GROUND);
    }

    /*
    each elbow state represents some range of angles with a corresponding max length represented by an extender state
     */

    public enum ElbowState {
        // the state of being inside the robot in front of the rotating plate
        IN_BELLY,
        // the state of being outside the robot for placement
        OUT_ROBOT,
        // the state of being in front of the plates wall, this should not be a permanent state on purpose
        WALL_ZONE,
        // this state should not be possible and is either used to stop dangerous movement or to signal a bug
        OUT_OF_BOUNDS
    }

    public void setMotorVoltage (double voltage){
        motor.setVoltage(voltage);
    }

}
