package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Extender extends GBSubsystem {


    private static ExtenderState state = ExtenderState.IN_ROBOT_BELLY_LENGTH;

    private ProfiledPIDController profiledPIDController;
    private static Extender instance;
    private GBSparkMax motor;

    public static Extender getInstance() {
        if (instance == null) {
            init();
            SmartDashboard.putBoolean("extender initialized via getinstance", true);
        }
        return instance;
    }

    public static void init(){
        instance = new Extender();
    }

    private Extender() {
        motor = new GBSparkMax(RobotMap.telescopicArm.extender.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.getEncoder().setPosition(0); //maybe absolute encoder+*
        motor.config(new GBSparkMax.SparkMaxConfObject()
                .withPID(RobotMap.telescopicArm.extender.PID)
                .withPositionConversionFactor(RobotMap.telescopicArm.extender.CONVERSION_FACTOR)
                .withIdleMode(CANSparkMax.IdleMode.kBrake)
                .withRampRate(RobotMap.telescopicArm.extender.RAMP_RATE)
                .withCurrentLimit(RobotMap.telescopicArm.extender.CURRENT_LIMIT)
                .withVoltageComp(RobotMap.General.VOLTAGE_COMP_VAL)
        );
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, RobotMap.telescopicArm.extender.BACKWARDS_LIMIT);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.telescopicArm.extender.FORWARD_LIMIT);


        profiledPIDController = new ProfiledPIDController(
                RobotMap.telescopicArm.extender.PID.getKp(),
                RobotMap.telescopicArm.extender.PID.getKi(),
                RobotMap.telescopicArm.extender.PID.getKd(),
                RobotMap.telescopicArm.extender.CONSTRAINTS
        );
    }

    @Override
    public void periodic() {
        state = getHypotheticalState(getLength());

    }

    public static ExtenderState getHypotheticalState(double lengthInMeters) {

        if (lengthInMeters > RobotMap.telescopicArm.extender.FORWARD_LIMIT || lengthInMeters < RobotMap.telescopicArm.extender.BACKWARDS_LIMIT) {
            return ExtenderState.OUT_OF_BOUNDS;
        }else if (lengthInMeters < RobotMap.telescopicArm.extender.MAX_ENTRANCE_LENGTH){
            return ExtenderState.ENTRANCE_LENGTH;
        } else if (lengthInMeters < RobotMap.telescopicArm.extender.MAX_LENGTH_IN_ROBOT) {
            return ExtenderState.IN_ROBOT_BELLY_LENGTH;
        } else {
            return ExtenderState.OPEN;
        }
    }


    public static double getFeedForward(double wantedSpeed, double wantedAcceleration, double elbowAngle) {
        return getStaticFeedForward(elbowAngle) +
                RobotMap.telescopicArm.extender.kS * Math.signum(wantedSpeed) +
                RobotMap.telescopicArm.extender.kV * wantedSpeed +
                RobotMap.telescopicArm.extender.kA * wantedAcceleration;
    }

    public static double getStaticFeedForward (double elbowAngle){
        return Math.sin(elbowAngle - RobotMap.telescopicArm.elbow.STARTING_ANGLE_RELATIVE_TO_GROUND) * RobotMap.telescopicArm.extender.kG ;
    }
    private void setLengthByPID(double lengthInMeters) {;
        profiledPIDController.reset(getLength());
        profiledPIDController.setGoal(lengthInMeters);
        double feedForward = getFeedForward(
                profiledPIDController.getSetpoint().velocity, (profiledPIDController.getSetpoint().velocity - motor.getEncoder().getVelocity()) / RoborioUtils.getCurrentRoborioCycle(),Elbow.getInstance().getAngle());
        motor.getPIDController().setReference(profiledPIDController.getSetpoint().velocity, CANSparkMax.ControlType.kVelocity, 0, feedForward);
    }

    public void moveTowardsLength(double lengthInMeters) {
        // going out of bounds should not be allowed
        if (getHypotheticalState(lengthInMeters) == ExtenderState.OUT_OF_BOUNDS) {
            stop();
            return;
        }
        if (Elbow.getInstance().getState() == Elbow.ElbowState.IN_ROBOT && getHypotheticalState(lengthInMeters) == ExtenderState.OPEN) {
            setLengthByPID(RobotMap.telescopicArm.extender.MAX_ENTRANCE_LENGTH);
        }
        if (Elbow.getInstance().getState() == Elbow.ElbowState.IN_FRONT_OF_ENTRANCE && getHypotheticalState(lengthInMeters) != ExtenderState.ENTRANCE_LENGTH){
            stop();
        }
        else {
            setLengthByPID(lengthInMeters);
        }
    }


    public double getLength() {
        return motor.getEncoder().getPosition();
    }

    public ExtenderState getState() {
        return state;
    }

    public void stop() {
        motor.set(0);
    }

    public enum ExtenderState {
        //see elbow state first
        //the state corresponding to IN_ROBOT
        IN_ROBOT_BELLY_LENGTH,
        //the state corresponding to OUT_ROBOT
        OPEN,
        //the state corresponding to IN_FRONT_OF_ENTRANCE
        ENTRANCE_LENGTH,
        // this state should not be possible and is either used to stop dangerous movement or to signal a bug
        OUT_OF_BOUNDS
    }

    public boolean isAtLength(double wantedLengthInMeters) {
        return Math.abs(getLength() - wantedLengthInMeters) <= RobotMap.telescopicArm.extender.LENGTH_TOLERANCE;
    }

    public void setMotorVoltage (double voltage){
        motor.setVoltage(voltage);
    }


}


