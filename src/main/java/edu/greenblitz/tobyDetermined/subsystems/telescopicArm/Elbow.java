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
    public static final double forwardEntranceAngle = Units.degreesToRadians(69);
    public static final double backWardEntranceAngle = Units.degreesToRadians(-69);

    public static final int motorID = 1;
    public static final PIDObject elbowAngularPID = new PIDObject();
    public static final double motorAngleLimit = Math.PI;

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
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward,(float) motorAngleLimit);
    }

    public void setAngle(double angleInRads){
        switch (Extender.getInstance().getState()){
            case OPEN:
                if (getHypotheticalState(angleInRads) == ElbowState.OUT_ROBOT){
                    motor.getPIDController().setReference(angleInRads, CANSparkMax.ControlType.kPosition);
                }
                break;

            case CLOSED:
                motor.getPIDController().setReference(angleInRads, CANSparkMax.ControlType.kPosition);
                break;
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
        if(angleInRads >= forwardEntranceAngle){
            return ElbowState.OUT_ROBOT;
        }else{
            return ElbowState.IN_ROBOT;
        }
    }

    public enum ElbowState {
        IN_ROBOT,OUT_ROBOT;
    }

}
