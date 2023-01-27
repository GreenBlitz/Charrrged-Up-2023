package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBSparkMax;

import javax.crypto.MacSpi;

public class Elbow extends GBSubsystem {


    private static Elbow instance;

    private elbowState state = elbowState.IN_ROBOT;
    private GBSparkMax motor;

    private static final int motorID = 1;
    private static final PIDObject elbowAngularPID = new PIDObject();
    private static final double forwardLimit = Math.PI ;

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
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward,(float) forwardLimit);
    }
    public void setAngle (double angleInRands){
        motor.getPIDController().setReference(angleInRands, CANSparkMax.ControlType.kPosition);
    }

    public double getAngle (){
        return motor.getEncoder().getPosition();
    }

    public elbowState getState (){
        return state;
    }

    @Override
    public void periodic() {
        if(getAngle() >= 45){
            state = elbowState.OUT_ROBOT;
        }else{
            state = elbowState.IN_ROBOT;
        }
    }

    public enum elbowState {
        IN_ROBOT,OUT_ROBOT;
    }

}
