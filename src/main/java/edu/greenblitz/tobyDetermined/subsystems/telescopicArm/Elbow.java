package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;


public class Elbow extends GBSubsystem {


    private static Elbow instance;
    public ElbowState state = ElbowState.IN_BELLY;
    public GBSparkMax motor;
    private double lastSpeed;

    public static Elbow getInstance() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    public static void init(){
        instance = new Elbow();
    }

    private Elbow() {
        motor = new GBSparkMax(RobotMap.TelescopicArm.Elbow.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.config(new GBSparkMax.SparkMaxConfObject()
                .withPID(RobotMap.TelescopicArm.Elbow.PID)
                .withIdleMode(CANSparkMax.IdleMode.kBrake)
                .withRampRate(RobotMap.TelescopicArm.Elbow.MOTOR_RAMP_RATE)
                .withCurrentLimit(RobotMap.TelescopicArm.Elbow.CURRENT_LIMIT)
                .withVoltageComp(RobotMap.General.VOLTAGE_COMP_VAL)
        );

        motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).setPositionConversionFactor(RobotMap.TelescopicArm.Elbow.POSITION_CONVERSION_FACTOR);
        motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).setVelocityConversionFactor(RobotMap.TelescopicArm.Elbow.VELOCITY_CONVERSION_FACTOR);

        motor.getPIDController().setFeedbackDevice(motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle));
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT);

        lastSpeed = 0;
    }

    public void moveTowardsAngle(double angleInRads) {
        // going out of bounds should not be allowed
        if (edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.getHypotheticalState(angleInRads) == ElbowState.OUT_OF_BOUNDS){
            if(angleInRads <= RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT){
                moveTowardsAngle(RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT);
            }else if(angleInRads >= RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT){
                moveTowardsAngle(RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT);
            }else{
                stop();
            }
            System.err.println("arm Elbow is trying to move OUT OF BOUNDS");
            return;
        }

        //when moving between states the arm always passes through the IN_FRONT_OF_ENTRANCE zone and so length must be short enough
        // if its not short enough the arm will approach the start of the zone
        if(getState() != getHypotheticalState(angleInRads) &&
                edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.getInstance().getState() != edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.ExtenderState.IN_WALL_LENGTH){
            setAngleByPID(state == ElbowState.IN_BELLY ? RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE : RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE);
        }else {
            setAngleByPID(angleInRads);
        }
    }

    public void setAngleByPID(double goalAngle) {
        ProfiledPIDController pidController = RobotMap.TelescopicArm.Elbow.PID_CONTROLLER;
        pidController.reset(getAngle());
        pidController.setGoal(goalAngle);
        double feedForward = getFeedForward(
                pidController.getSetpoint().velocity, (pidController.getSetpoint().velocity - lastSpeed) / RoborioUtils.getCurrentRoborioCycle(),
                edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.getInstance().getLength(), getAngle()
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
        if (angleInRads > RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT || angleInRads < RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT) {
            return ElbowState.OUT_OF_BOUNDS;
        } else if (angleInRads > RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE && angleInRads < RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE) {
            return ElbowState.WALL_ZONE;
        } else if (angleInRads > RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE) {
            return ElbowState.OUT_ROBOT;
        }
        return ElbowState.IN_BELLY;

    }

    public boolean isAtAngle(double wantedAngle) {
        return Math.abs(getAngle() - wantedAngle) <= RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE;
    }

    public boolean isInTheSameState(double wantedAng) {
        return getHypotheticalState(getAngle()) == getHypotheticalState(wantedAng) && getHypotheticalState(wantedAng) != ElbowState.OUT_OF_BOUNDS;
    }

    public static double getFeedForward(double wantedAngularSpeed, double wantedAcc, double extenderLength,double elbowAngle) {
        double Kg = getStaticFeedForward(extenderLength,elbowAngle);
        return Kg + RobotMap.TelescopicArm.Elbow.kS * Math.signum(wantedAngularSpeed) + RobotMap.TelescopicArm.Elbow.kV * wantedAngularSpeed + RobotMap.TelescopicArm.Elbow.kA * wantedAcc;
    }
    public static double getStaticFeedForward(double extenderLength,double elbowAngle) {
        return (RobotMap.TelescopicArm.Elbow.MIN_Kg + (((RobotMap.TelescopicArm.Elbow.MAX_Kg - RobotMap.TelescopicArm.Elbow.MIN_Kg) * extenderLength)
                / RobotMap.TelescopicArm.Extender.EXTENDED_LENGTH)) * Math.cos(elbowAngle - RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND);
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
