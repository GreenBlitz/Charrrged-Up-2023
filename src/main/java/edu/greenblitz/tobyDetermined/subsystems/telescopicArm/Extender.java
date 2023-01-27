package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBSparkMax;

public class Extender extends GBSubsystem {
    private static final int EXTENDER_MOTOR_ID = 0;
    private static final int BACKWARDS_LIMIT = 0;
    private static final int FORWARD_LIMIT = 0;
    private static final double distanceBetweenHoles = 6.35;
    private static final double gearRation = 1 / 7.0;
    private static final double extenderConversionFactor = (RobotMap.General.Motors.SPARKMAX_TICKS_PER_RADIAN / gearRation) * distanceBetweenHoles;
    private static ExtenderState state = ExtenderState.CLOSED;

    private static final double maxLengthInRobot = 0.4;

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
        motor = new GBSparkMax(EXTENDER_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.getEncoder().setPosition(0);
        motor.config(new GBSparkMax.SparkMaxConfObject()
                .withPID(extenderPID)
                .withPositionConversionFactor(extenderConversionFactor) //fixme supposed to be true but im not sure
        );
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, BACKWARDS_LIMIT);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, FORWARD_LIMIT);
    }

    @Override
    public void periodic() {
        if (getLength() >=maxLengthInRobot){
            state = ExtenderState.OPEN;
        }else{
            state = ExtenderState.CLOSED;
        }

    }

    public void setLength(double lengthInMeters){
        switch (Elbow.getInstance().getState()){
            case IN_ROBOT:
                if (lengthInMeters < maxLengthInRobot){
                    motor.getPIDController().setReference(lengthInMeters, CANSparkMax.ControlType.kPosition);
                }
                break;

            case OUT_ROBOT:
                motor.getPIDController().setReference(lengthInMeters, CANSparkMax.ControlType.kPosition);
                break;
        }

        motor.getPIDController().setReference(lengthInMeters, CANSparkMax.ControlType.kPosition);
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
        CLOSED, OPEN
    }
}
