package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.util.Units;

public class Extender extends GBSubsystem {
    public static final int EXTENDER_MOTOR_ID = 0;
    public static final int BACKWARDS_LIMIT = 0;
    public static final double FORWARD_LIMIT = 0.6;
    public static final double distanceBetweenHoles = 6.35;
    public static final double outputGearAmountOfTeeth = 32;
    public static final double gearRatio = 1 / 7.0;

    public static final double maxLengthInRobot = 0.4;
    public static final PIDObject extenderPID = new PIDObject();
    public static final double extenderConversionFactor =
            (((RobotMap.General.Motors.SPARKMAX_TICKS_PER_RADIAN / gearRatio) * outputGearAmountOfTeeth) / (2 * Math.PI) ) * distanceBetweenHoles;

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
        motor = new GBSparkMax(EXTENDER_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.getEncoder().setPosition(0); //maybe absolute encoder+*
        motor.config(new GBSparkMax.SparkMaxConfObject()
                .withPID(extenderPID)
                .withPositionConversionFactor(extenderConversionFactor)
        );
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, BACKWARDS_LIMIT);
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float) FORWARD_LIMIT);
    }

    @Override
    public void periodic() {
        state = getHypotheticalState(getLength());

    }

    public static ExtenderState getHypotheticalState (double lengthInMeters) {

        if(lengthInMeters > FORWARD_LIMIT || lengthInMeters < BACKWARDS_LIMIT){
            return ExtenderState.OUT_OF_BOUNDS;
        }else if(lengthInMeters >= maxLengthInRobot){
            return ExtenderState.OPEN;
        }else{
            return ExtenderState.CLOSED;
        }
    }

    public void setLength(double lengthInMeters){

        if(getHypotheticalState(lengthInMeters) == ExtenderState.OUT_OF_BOUNDS){
            return;
        }

        if(Elbow.getHypotheticalState(Elbow.getInstance().getAngle()) == Elbow.ElbowState.IN_ROBOT && getHypotheticalState(lengthInMeters) == ExtenderState.OPEN){
            stop();
        }else{
            motor.getPIDController().setReference(lengthInMeters, CANSparkMax.ControlType.kPosition);

        }

    }

    public boolean isOutOfBounds (){
        return state == ExtenderState.OUT_OF_BOUNDS;
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
        private final double angleInDegrees;
        public final double angleInRadians;

        presetPositions(double distance, double angle) {
            this.distance = distance;
            this.angleInDegrees = angle;
            this.angleInRadians = Units.degreesToRadians(angleInDegrees);

        }
    }
}


