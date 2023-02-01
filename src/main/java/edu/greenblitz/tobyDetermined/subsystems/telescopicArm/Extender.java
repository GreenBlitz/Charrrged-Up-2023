package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.util.Units;

public class Extender extends GBSubsystem {
    private static final int EXTENDER_MOTOR_ID = 0;
    private static final int BACKWARDS_LIMIT = 0;
    private static final int FORWARD_LIMIT = 0;
    private static final double distanceBetweenHoles = 6.35;
    private static final double lastGearTeethNumber = 32;
    private static final double gearRatio = 1 / 7.0;
    private static final double extenderConversionFactor =
            (((RobotMap.General.Motors.SPARKMAX_TICKS_PER_RADIAN / gearRatio) * lastGearTeethNumber) /
                    RobotMap.General.Motors.SPARKMAX_TICKS_PER_RADIAN) * distanceBetweenHoles;



    ; //todo this is wrong!
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
        state = getHypotheticalState(getLength());

    }

    public static ExtenderState getHypotheticalState (double lengthInMeters){
        if(lengthInMeters >= maxLengthInRobot){
            return ExtenderState.OPEN;
        }else{
            return ExtenderState.CLOSED;
        }
    }

    public void setLength(double lengthInMeters){


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
        CLOSED, OPEN
    }

    public enum presetPositions {
        //height in meters
        //angle in degrees
        coneHigh(1.545639026, 49.1),
        coneMid(1.04560987, 56.30993247),
        CubeHigh(1.352811886, 41.70388403),
        CubeMid(83.45058418, 34.59228869),
        low(0, 5),
        ;
        public final double distance;
        public final double angleInDegrees;
        public final double angleInRadians;

        presetPositions(double distance, double angle) {
            this.distance = distance;
            this.angleInDegrees = angle;
            this.angleInRadians = Units.degreesToRadians(angleInDegrees);

        }
    }
}


