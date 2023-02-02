package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.util.Units;


public class Elbow extends GBSubsystem {


    private static Elbow instance;
    public ElbowState state = ElbowState.IN_ROBOT;
    public GBSparkMax motor;
    public static final double EntranceAngle = Units.degreesToRadians(69);

    public static final int motorID = 1;
    public static final PIDObject elbowAngularPID = new PIDObject();
    public static final double forwardAngleLimit = Units.degreesToRadians(270);
    public static final double backwardAngleLimit = Units.degreesToRadians(270);


    public static Elbow getInstance(){
        if(instance == null){
            instance = new Elbow();
        }
        return instance;
    }

    private Elbow(){
        motor = new GBSparkMax(motorID, CANSparkMaxLowLevel.MotorType.kBrushless);

        motor.config(new GBSparkMax.SparkMaxConfObject()
                .withPID(elbowAngularPID)
                .withPositionConversionFactor(RobotMap.General.Motors.SPARKMAX_TICKS_PER_RADIAN)
        );
        motor.getEncoder().setPosition(0); //resetPosition
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward,(float) forwardAngleLimit);
    }

    public void setAngle(double angleInRads) {
        if (Extender.getInstance().getState() == Extender.ExtenderState.OPEN && getHypotheticalState(angleInRads) == ElbowState.IN_ROBOT ) {
            stop();
        } else {
            motor.getPIDController().setReference(angleInRads, CANSparkMax.ControlType.kPosition);
        }

    }

    public double getAngle(){
        return motor.getEncoder().getPosition();
    }

    public ElbowState getState(){
        return state;
    }

    public void stop (){
        motor.set(0);
    }
    @Override
    public void periodic() {
        state = getHypotheticalState(getAngle());
    }

    public static ElbowState getHypotheticalState(double angleInRads){
        if(angleInRads > forwardAngleLimit || angleInRads < backwardAngleLimit){
            return ElbowState.OUT_OF_BOUNDS;
        }
        if(angleInRads >= EntranceAngle){
            return ElbowState.OUT_ROBOT;
        }
        return ElbowState.IN_ROBOT;

    }

    public enum ElbowState {
        IN_ROBOT,OUT_ROBOT,OUT_OF_BOUNDS
    }

}
