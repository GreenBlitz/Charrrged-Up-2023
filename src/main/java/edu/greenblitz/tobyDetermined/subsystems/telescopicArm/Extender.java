package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.motors.GBSparkMax;

public class Extender extends GBSubsystem {


    private static ExtenderState state = ExtenderState.CLOSED;


    private static Extender instance;
    private GBSparkMax motor;

    public static Extender getInstance (){
        if(instance == null){
            instance = new Extender();
        }
        return instance;
    }

    private Extender(){
        motor = new GBSparkMax(RobotMap.telescopicArm.extender.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.getEncoder().setPosition(0); //maybe absolute encoder+*
        motor.config(new GBSparkMax.SparkMaxConfObject()
                .withPID(RobotMap.telescopicArm.extender.PID)
                .withPositionConversionFactor(RobotMap.telescopicArm.extender.EXTENDER_CONVERSION_FACTOR)
        );
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, RobotMap.telescopicArm.extender.BACKWARDS_LIMIT);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.telescopicArm.extender.FORWARD_LIMIT);
    }

    @Override
    public void periodic() {
        state = getHypotheticalState(getLength());

    }

    public static ExtenderState getHypotheticalState (double lengthInMeters) {

        if(lengthInMeters > RobotMap.telescopicArm.extender.FORWARD_LIMIT || lengthInMeters < RobotMap.telescopicArm.extender.BACKWARDS_LIMIT){
            return ExtenderState.OUT_OF_BOUNDS;
        }else if(lengthInMeters >= RobotMap.telescopicArm.extender.MAX_LENGTH_IN_ROBOT){
            return ExtenderState.OPEN;
        }else{
            return ExtenderState.CLOSED;
        }
    }

    public void setLength(double lengthInMeters){

        if(getHypotheticalState(lengthInMeters) == ExtenderState.OUT_OF_BOUNDS){
            stop();
            return;
        }

        if(Elbow.getHypotheticalState(Elbow.getInstance().getAngle()) == Elbow.ElbowState.IN_ROBOT && getHypotheticalState(lengthInMeters) == ExtenderState.OPEN){
            stop();
        }else{
            motor.getPIDController().setReference(lengthInMeters, CANSparkMax.ControlType.kPosition);

        }

    }


    public double getLength (){
        return motor.getEncoder().getPosition();
    }

    public ExtenderState getState(){
        return state;
    }

    public void stop (){
        motor.set(0);
    }

    enum ExtenderState{
        CLOSED, OPEN, OUT_OF_BOUNDS
    }

    public boolean isAtLength (double wantedLengthInMeters){
        return Math.abs(getLength() - wantedLengthInMeters) <= RobotMap.telescopicArm.extender.LENGTH_TOLERANCE;
    }

}


