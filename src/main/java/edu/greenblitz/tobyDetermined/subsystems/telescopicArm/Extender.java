package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBSparkMax;

public class Extender extends GBSubsystem {
    private static final int EXTENDER_MOTOR_ID = 0;
    private static final int BACKWARDS_LIMIT = 0;
    private static final int FORWARD_LIMIT = 0;
    private static ExtenderState state = ExtenderState.CLOSED;

    private static final PIDObject extenderPID = new PIDObject();
    private static Extender instance;
    private GBSparkMax motor;

    public static Extender getInstance (){
        if(instance == null){
            instance = new Extender();
        }
        return instance;
    }

    private Extender(){
        motor = new GBSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.getEncoder().setPosition(0);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, BACKWARDS_LIMIT);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, FORWARD_LIMIT);
    }

    @Override
    public void periodic() {
        
    }

    public void setLength(double lengthInMeters){

        motor.getPIDController().setReference(lengthInMeters, CANSparkMax.ControlType.kPosition);
    }

    public double getLength (){
        return motor.getEncoder().getPosition();
    }

    public ExtenderState getState(){
        return state;
    }

    enum ExtenderState{
        CLOSED, SEMI_OPEN, OPEN
    }
}
