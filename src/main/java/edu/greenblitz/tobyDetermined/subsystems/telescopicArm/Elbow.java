package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elbow extends GBSubsystem {


    private static Elbow instance;
    public ElbowState state = ElbowState.IN_BELLY;
    public GBSparkMax motor;
    private ProfiledPIDController profileGenerator;  // this does not actually use the pid controller only the setpoint
    private double lastSpeed;
    private double debugLastFF;

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
        motor.config(RobotMap.TelescopicArm.Elbow.ELBOW_CONFIG_OBJECT);

        motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).setPositionConversionFactor(RobotMap.TelescopicArm.Elbow.POSITION_CONVERSION_FACTOR);
        motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).setVelocityConversionFactor(RobotMap.TelescopicArm.Elbow.VELOCITY_CONVERSION_FACTOR);

        motor.getPIDController().setFeedbackDevice(motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle));
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT);

        profileGenerator = new ProfiledPIDController(
                0,0,0,RobotMap.TelescopicArm.Elbow.CONSTRAINTS
        );
        profileGenerator.setTolerance(RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE);
        lastSpeed = 0;
    }

    @Override
    public void periodic() {
        state = getHypotheticalState(getAngle());
        lastSpeed = getVelocity();
        updatePIDController(Dashboard.getInstance().getElbowPID());

    }

    public void updatePIDController(PIDObject pidObject){
        motor.configPID(pidObject);
    }

    public void moveTowardsAngle(double angleInRads) {
        // going out of bounds should not be allowed
        if (getHypotheticalState(angleInRads) == ElbowState.OUT_OF_BOUNDS){
            if(angleInRads < RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT){
                moveTowardsAngle(RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT);
            }else if(angleInRads > RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT){
                moveTowardsAngle(RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT);
            }else{
                stop();
            }
            Console.log("OUT OF BOUNDS", "arm Elbow is trying to move OUT OF BOUNDS" );
            return;
        }
        if (getHypotheticalState(angleInRads) == ElbowState.FLOOR_ZONE && Extender.getInstance().getState() == Extender.ExtenderState.IN_FLOOR_LENGTH){
            //going in floor
            moveTowardsAngle(RobotMap.TelescopicArm.Elbow.END_FLOOR_ZONE_ANGLE);
            Console.log("FLOOR ZONE", "arm elbow is trying to move toward floor");
        }

        //when moving between states the arm always passes through the IN_FRONT_OF_ENTRANCE zone and so length must be short enough
        // if its not short enough the arm will approach the start of the zone
        if(getState() != getHypotheticalState(angleInRads) &&
                Extender.getInstance().getState() != Extender.ExtenderState.IN_WALL_LENGTH){
            moveTowardsAngle(state == ElbowState.IN_BELLY ? RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE : RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE);
        }else {
            moveTowardsAngle(angleInRads);
        }
    }

    public void setAngleByPID(double goalAngle) {
        profileGenerator.reset(getAngle(), getVelocity());
        profileGenerator.setGoal(goalAngle);
        double feedForward = getFeedForward(
                profileGenerator.getSetpoint().velocity, (profileGenerator.getSetpoint().velocity - lastSpeed) / RoborioUtils.getCurrentRoborioCycle(),
                Extender.getInstance().getLength(), getAngle()
        );
        SmartDashboard.putNumber("Elbow FF", feedForward);  //todo - its for debugging, remove when done
        motor.getPIDController().setReference(profileGenerator.getSetpoint().velocity, CANSparkMax.ControlType.kVelocity, 0, feedForward);
        debugLastFF = feedForward;
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

    public double getVelocity (){
        return motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).getVelocity();
    }

    public static ElbowState getHypotheticalState(double angleInRads) {
        if (angleInRads > RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT || angleInRads < RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT) {
            return ElbowState.OUT_OF_BOUNDS;
        } else if (angleInRads > RobotMap.TelescopicArm.Elbow.END_FLOOR_ZONE_ANGLE) {
            return ElbowState.OUT_ROBOT;
        } else if (angleInRads > RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE) {
            return ElbowState.FLOOR_ZONE;
        } else if (angleInRads > RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE) {
            return ElbowState.WALL_ZONE;
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

    public double getDebugLastFF(){
        return debugLastFF;
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
        OUT_OF_BOUNDS,

        FLOOR_ZONE
    }

    public void setMotorVoltage (double voltage){
        motor.setVoltage(voltage);
    }

    public PIDObject getPID(){
        return new PIDObject().withKp(motor.getPIDController().getP()).withKi(motor.getPIDController().getI()).withKd(motor.getPIDController().getD());
    }

}
