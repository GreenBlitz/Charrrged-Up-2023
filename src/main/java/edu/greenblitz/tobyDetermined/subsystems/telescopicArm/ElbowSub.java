package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.filter.MedianFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.*;

public class ElbowSub extends GBSubsystem {


    private static ElbowSub instance;
    public ElbowState state = ElbowState.IN_BELLY;
    public GBSparkMax motor;
    private double debugLastFF;
    public double goalAngle;
    private MedianFilter absolutAngFilter;
    
    /*double sprocketRatio = 16.0/60;
    double gearRatio = 1.0/30;
    double combinedGearRatio = sprocketRatio * gearRatio;*/

    private boolean debug = false;

    public static ElbowSub getInstance() {
        init();
        return instance;
    }

    public static void init(){
        if (instance == null) {
            instance = new ElbowSub();
        }
    }

    public double startingValue;
    
    private ElbowSub() {
        motor = new GBSparkMax(RobotMap.TelescopicArm.Elbow.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.config(RobotMap.TelescopicArm.Elbow.ELBOW_CONFIG_OBJECT);

        motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).setPositionConversionFactor(RobotMap.TelescopicArm.Elbow.ABSOLUTE_POSITION_CONVERSION_FACTOR);
        motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).setVelocityConversionFactor(RobotMap.TelescopicArm.Elbow.ABSOLUTE_VELOCITY_CONVERSION_FACTOR);
        
        motor.getEncoder().setPositionConversionFactor(RELATIVE_POSITION_CONVERSION_FACTOR);//not the actual gear ratio, weird estimation
        motor.getEncoder().setVelocityConversionFactor(RELATIVE_VELOCITY_CONVERSION_FACTOR);
    
        startingValue = motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).getPosition();
        motor.getPIDController().setFeedbackDevice(motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle));
        motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT);


        goalAngle = getAngleRadians();
        if(debug){
            debugSoftLimit();
        }
        accTimer = new Timer();
        accTimer.start();

        absolutAngFilter = new MedianFilter(RESET_MEDIAN_SIZE);
    }
    
    private Timer accTimer;
    private double lastSpeed;

    @Override
    public void periodic() {
        state = getHypotheticalState(getAngleRadians());
        SmartDashboard.putNumber("voltage", motor.getAppliedOutput() * Battery.getInstance().getCurrentVoltage());
        SmartDashboard.putNumber("velocity",getVelocity());
        SmartDashboard.putNumber("position",getAngleRadians());
        SmartDashboard.putNumber("current", motor.getOutputCurrent());
        SmartDashboard.putNumber("ratio", (motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).getPosition() - startingValue) / (motor.getEncoder().getPosition() - startingValue));
        if (accTimer.advanceIfElapsed(0.15)) {
            SmartDashboard.putNumber("curr acc",
                    (getVelocity() - lastSpeed) / (0.15 + accTimer.get())
            );
            lastSpeed = getVelocity();
        }
    
    }

    public void resetEncoder(){
        motor.getEncoder().setPosition(absolutAngFilter.calculate(motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).getPosition()));
    }

    public void updatePIDController(PIDObject pidObject){
        motor.configPID(pidObject);
    }

    private void debugSoftLimit(){
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 0.3);
    }

    public void debugSetPower(double power){
        motor.set(power);
    }

    public void moveTowardsAngleRadians(double angleInRads, double feedForward) {
        setAngleRadiansByPID(getLegalGoalAngle(angleInRads), feedForward);
        goalAngle = angleInRads;
    }

    public void moveTowardsAngleRadians(double angleInRads){
        moveTowardsAngleRadians(angleInRads, 0);
    }

    public double getLegalGoalAngle(double angleInRads){
        // going out of bounds should not be allowed
        if (getHypotheticalState(angleInRads) == ElbowState.FORWARD_OUT_OF_BOUNDS || getHypotheticalState(angleInRads) == ElbowState.BACKWARD_OUT_OF_BOUNDS){
            Console.log("OUT OF BOUNDS", "arm Elbow is trying to move OUT OF BOUNDS" );
            if(angleInRads < RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT){
                return (RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT + RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE);
            }else {
                return (RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT - RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE);
            }
        }

        else if (getState() == ElbowState.WALL_ZONE && Extender.getInstance().getState() != Extender.ExtenderState.IN_WALL_LENGTH) {
            return getAngleRadians();
            //when moving between states the arm always passes through the IN_FRONT_OF_ENTRANCE zone and so length must be short enough
            // if it's not short enough the arm will approach the start of the zone
        }else if((getState() != getHypotheticalState(angleInRads)) &&
                Extender.getInstance().getState() != Extender.ExtenderState.IN_WALL_LENGTH){
            return (state == ElbowState.IN_BELLY ? RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE - RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE : RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE+ RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE);
        }else {
            return (angleInRads);
        }

    }

    public void setAngleRadiansByPID(double goalAngle, double feedForward) {
        motor.getPIDController().setReference(goalAngle, CANSparkMax.ControlType.kPosition, 0, feedForward);
         debugLastFF = feedForward;
    }

    public double getAngleRadians() {
//        return motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).getPosition();
        return (motor.getEncoder().getPosition());
    }

    public ElbowState getState() {
        return state;
    }

    public void stop() {
        motor.set(0);
    }

    public double getVelocity (){
        return motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).getVelocity();
    }

    public static ElbowState getHypotheticalState(double angleInRads) {
        if (angleInRads < ElbowState.BACKWARD_OUT_OF_BOUNDS.maxAngle){
            return ElbowState.BACKWARD_OUT_OF_BOUNDS;
        }
        else if (angleInRads < ElbowState.IN_BELLY.maxAngle){
            return ElbowState.IN_BELLY;
        }else if (angleInRads < ElbowState.WALL_ZONE.maxAngle){
            return ElbowState.WALL_ZONE;
        }else if(angleInRads < ElbowState.OUT_ROBOT.maxAngle){
            return ElbowState.OUT_ROBOT;
        }
        return ElbowState.FORWARD_OUT_OF_BOUNDS;
    }

    public boolean isAtAngle(double wantedAngle) {
        return Math.abs(getAngleRadians() - wantedAngle) < RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE;
    }

    public boolean isAtAngle(){
        return isAtAngle(goalAngle);
    }

    public boolean isNotMoving(){
        return Math.abs(getVelocity()) < RobotMap.TelescopicArm.Elbow.ANGULAR_VELOCITY_TOLERANCE;
    }

    public boolean isInTheSameState(double wantedAng) {
        return getHypotheticalState(getAngleRadians()) == getHypotheticalState(wantedAng) && (getHypotheticalState(wantedAng) != ElbowState.FORWARD_OUT_OF_BOUNDS || getHypotheticalState(wantedAng) != ElbowState.BACKWARD_OUT_OF_BOUNDS);
    }

    public static double getStaticFeedForward(double extenderLength,double elbowAngle) {
        return (RobotMap.TelescopicArm.Elbow.MIN_Kg + (((RobotMap.TelescopicArm.Elbow.MAX_Kg - RobotMap.TelescopicArm.Elbow.MIN_Kg) * extenderLength)
                / RobotMap.TelescopicArm.Elbow.MAX_KG_MEASUREMENT_LENGTH)) * Math.cos(elbowAngle + RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND);

    }

    public double getDebugLastFF(){
        return debugLastFF;
    }


    /*
    each elbow state represents some range of angles with a corresponding max length represented by an extender state
     */

    public enum ElbowState {
        // the state of being inside the robot in front of the rotating plate
        IN_BELLY(STARTING_WALL_ZONE_ANGLE),
        // the state of being outside the robot for placement
        OUT_ROBOT(FORWARD_ANGLE_LIMIT),
        // the state of being in front of the plates wall, this should not be a permanent state on purpose
        WALL_ZONE(END_WALL_ZONE_ANGLE),
        // this state should not be possible and is either used to stop dangerous movement or to signal a bug
        FORWARD_OUT_OF_BOUNDS(Double.POSITIVE_INFINITY),

        BACKWARD_OUT_OF_BOUNDS(BACKWARD_ANGLE_LIMIT);
        private double maxAngle;
        private ElbowState(double maxAngle){
            this.maxAngle = maxAngle;
        }

        public boolean largerThen(ElbowState checkState){
            return maxAngle > checkState.maxAngle;
        }

        public boolean smallerThen(ElbowState checkState){
            return maxAngle < checkState.maxAngle;
        }
        public boolean smallerOrEqualTo(ElbowState checkState){
            return maxAngle <= checkState.maxAngle;
        }
    }

    public void setMotorVoltage (double voltage){
        motor.setVoltage(voltage);
    }

    public PIDObject getPID(){
        return new PIDObject().withKp(motor.getPIDController().getP()).withKi(motor.getPIDController().getI()).withKd(motor.getPIDController().getD());
    }
    
    public double getGoalAngle() {
        return goalAngle;
    }

    public void setIdleMode (CANSparkMax.IdleMode idleMode){
        motor.setIdleMode(idleMode);
    }
}
