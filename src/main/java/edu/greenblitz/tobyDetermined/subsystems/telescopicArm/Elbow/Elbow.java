package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.tobyDetermined.Robot;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.greenblitz.utils.PIDObject;
import edu.wpi.first.math.filter.MedianFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.littletonrobotics.junction.Logger;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.*;

public class Elbow extends GBSubsystem {


    private static Elbow instance;
    public ElbowState state = ElbowState.IN_BELLY;
    private double debugLastFF;
    public double goalAngle;
    private MedianFilter absolutAngFilter;
    
    /*double sprocketRatio = 16.0/60;
    double gearRatio = 1.0/30;
    double combinedGearRatio = sprocketRatio * gearRatio;*/

    private boolean debug = false;


    private IElbow elbow;
    private ElbowInputsAutoLogged elbowInputs;

    public static Elbow getInstance() {
        init();
        return instance;
    }

    public static void init(){
        if (instance == null) {
            instance = new Elbow();
        }
    }
    public double startingValue;
    
    private Elbow() {

        elbow = ElbowFactory.create();
        elbowInputs = new ElbowInputsAutoLogged();
        elbow.updateInputs(elbowInputs);

        startingValue = elbowInputs.absoluteEncoderPosition;

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
<<<<<<< HEAD:src/main/java/edu/greenblitz/tobyDetermined/subsystems/telescopicArm/Elbow.java
        SmartDashboard.putNumber("voltage", motor.getAppliedOutput() * Battery.getInstance().getCurrentVoltage());
        SmartDashboard.putNumber("velocity",getVelocity());
        SmartDashboard.putNumber("position",getAngleRadians());
        SmartDashboard.putNumber("current", motor.getOutputCurrent());
        SmartDashboard.putNumber("ratio", (motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).getPosition() - startingValue) / (motor.getEncoder().getPosition() - startingValue));
=======
        SmartDashboard.putNumber("voltage", elbowInputs.appliedOutput);
        SmartDashboard.putNumber("velocity", elbowInputs.velocity);
        SmartDashboard.putNumber("position", elbowInputs.position);
        SmartDashboard.putNumber("current", elbowInputs.outputCurrent);
        SmartDashboard.putNumber("ratio", (elbowInputs.absoluteEncoderPosition - startingValue) / (elbowInputs.position - startingValue));
    
>>>>>>> origin/master:src/main/java/edu/greenblitz/tobyDetermined/subsystems/telescopicArm/Elbow/Elbow.java
        if (accTimer.advanceIfElapsed(0.15)) {
            SmartDashboard.putNumber("curr acc",
                    (getVelocity() - lastSpeed) / (0.15 + accTimer.get())
            );
            lastSpeed = getVelocity();
        }

        elbow.updateInputs(elbowInputs);
        Logger.getInstance().processInputs("Elbow", elbowInputs);
    }

    public void resetEncoder(){
       elbow.setPosition(absolutAngFilter.calculate(elbowInputs.absoluteEncoderPosition));
    }

    private void debugSoftLimit(){
        elbow.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, DEBUG_ANGLE_LIMIT);
    }

    public void debugSetPower(double power){
        elbow.setPower(power);
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
                return (RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT + (RobotMap.ROBOT_TYPE != Robot.RobotType.SIMULATION ?  RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE : Simulation.SIM_ANGLE_TOLERANCE ));
            }else {
                return (RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT -  (RobotMap.ROBOT_TYPE != Robot.RobotType.SIMULATION ?  RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE : Simulation.SIM_ANGLE_TOLERANCE ));
            }
        }

        else if (getState() == ElbowState.WALL_ZONE && Extender.getInstance().getState() != Extender.ExtenderState.IN_WALL_LENGTH) {
            return getAngleRadians();
            //when moving between states the arm always passes through the IN_FRONT_OF_ENTRANCE zone and so length must be short enough
<<<<<<< HEAD:src/main/java/edu/greenblitz/tobyDetermined/subsystems/telescopicArm/Elbow.java
            // if it's not short enough the arm will approach the start of the zone
        }else if((getState() != getHypotheticalState(angleInRads))
                && Extender.getInstance().getState() != Extender.ExtenderState.IN_WALL_LENGTH){
            return (state == ElbowState.IN_BELLY ? RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE - RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE : RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE+ RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE);
=======
            // if its not short enough the arm will approach the start of the zone
        } else if((getState() != getHypotheticalState(angleInRads)) &&
                Extender.getInstance().getState() != Extender.ExtenderState.IN_WALL_LENGTH){
            return (state == ElbowState.IN_BELLY ? RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE - (RobotMap.ROBOT_TYPE != Robot.RobotType.SIMULATION ?  RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE : Simulation.SIM_ANGLE_TOLERANCE ) : RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE+ (RobotMap.ROBOT_TYPE != Robot.RobotType.SIMULATION ?  RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE : Simulation.SIM_ANGLE_TOLERANCE ));
>>>>>>> origin/master:src/main/java/edu/greenblitz/tobyDetermined/subsystems/telescopicArm/Elbow/Elbow.java
        }else {
            return (angleInRads);
        }

    }

    public void setAngleRadiansByPID(double goalAngle, double feedForward) {
        elbow.setAngleRadiansByPID(goalAngle, feedForward);
        debugLastFF = feedForward;
    }

    public double getAngleRadians() {
//        return motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).getPosition();
<<<<<<< HEAD:src/main/java/edu/greenblitz/tobyDetermined/subsystems/telescopicArm/Elbow.java
        return (motor.getEncoder().getPosition());
=======
        return elbowInputs.position;
>>>>>>> origin/master:src/main/java/edu/greenblitz/tobyDetermined/subsystems/telescopicArm/Elbow/Elbow.java
    }

    public ElbowState getState() {
        return state;
    }

    public void stop() {
        elbow.setPower(0);
    }

    public double getVelocity (){
        return elbowInputs.absoluteEncoderVelocity;
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
        return Math.abs(getAngleRadians() - wantedAngle) <  (RobotMap.ROBOT_TYPE != Robot.RobotType.SIMULATION ?  RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE : Simulation.SIM_ANGLE_TOLERANCE );
    }

    public boolean isAtAngle(){
        return isAtAngle(goalAngle);
    }

    public boolean isNotMoving(){
        return Math.abs(getVelocity()) < RobotMap.TelescopicArm.Elbow.ANGULAR_VELOCITY_TOLERANCE;
    }
    
    public void brake(){
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }
    public boolean isInTheSameState(double wantedAng) {
        return getHypotheticalState(getAngleRadians()) == getHypotheticalState(wantedAng) && (getHypotheticalState(wantedAng) != ElbowState.FORWARD_OUT_OF_BOUNDS || getHypotheticalState(wantedAng) != ElbowState.BACKWARD_OUT_OF_BOUNDS);
    }

    public static double getStaticFeedForward(double extenderLength,double elbowAngle) {
<<<<<<< HEAD:src/main/java/edu/greenblitz/tobyDetermined/subsystems/telescopicArm/Elbow.java
        return (RobotMap.TelescopicArm.Elbow.MIN_Kg + (((RobotMap.TelescopicArm.Elbow.MAX_Kg - RobotMap.TelescopicArm.Elbow.MIN_Kg ) * extenderLength)
                / RobotMap.TelescopicArm.Elbow.MAX_KG_MEASUREMENT_LENGTH)) * Math.cos(elbowAngle + RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND);

=======
        return RobotMap.ROBOT_TYPE != Robot.RobotType.SIMULATION ?  (RobotMap.TelescopicArm.Elbow.MIN_Kg + (((RobotMap.TelescopicArm.Elbow.MAX_Kg - RobotMap.TelescopicArm.Elbow.MIN_Kg) * extenderLength)
                / RobotMap.TelescopicArm.Elbow.MAX_KG_MEASUREMENT_LENGTH)) * Math.cos(elbowAngle + RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND)
                : 0;
>>>>>>> origin/master:src/main/java/edu/greenblitz/tobyDetermined/subsystems/telescopicArm/Elbow/Elbow.java
    }
    public static double getDynamicFeedForward(double wantedvelocity,double extenderLength,double elbowAngle) {
        return getStaticFeedForward(extenderLength, elbowAngle) + kV * wantedvelocity + kS*Math.signum(wantedvelocity);
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

    public double getVoltage (){
        return motor.getAppliedOutput() * Battery.getInstance().getCurrentVoltage();
    }

    public void setMotorVoltage (double voltage){
        elbow.setVoltage(voltage);
    }

    public PIDObject getPID(){
        return new PIDObject().withKp(elbowInputs.kP).withKi(elbowInputs.kI).withKd(elbowInputs.kD);
    }
    public void setAngSpeed(double speed, double angle, double length) {
        motor.getPIDController().setReference(speed, CANSparkMax.ControlType.kVelocity, 0, getDynamicFeedForward(speed,length ,angle));
    }
    public double getGoalAngle() {
        return goalAngle;
    }

    public void setIdleMode (CANSparkMax.IdleMode idleMode){
        elbow.setIdleMode(idleMode);
    }

    public void setGoalAngle (double goalAngle){
        this.goalAngle = goalAngle;
    }
}
