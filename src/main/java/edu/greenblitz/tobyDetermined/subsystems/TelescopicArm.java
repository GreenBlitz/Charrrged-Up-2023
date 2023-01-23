package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.motors.GBSparkMax;

public class TelescopicArm extends GBSubsystem{

    GBSparkMax extensionMotor;
    GBSparkMax angleMotor;
    double currentPosition;
    double targetLength;
    double targetAngle;

    private static TelescopicArm instance;

    private TelescopicArm (){
        //some values.
        extensionMotor.config(new GBSparkMax.SparkMaxConfObject()
                .withRampRate(10)
                .withPID(RobotMap.telescopicArm.LIFTING_PID)
                .withIdleMode(CANSparkMax.IdleMode.kBrake)
                .withPositionConversionFactor(RobotMap.telescopicArm.MOTOR_TICKS_TO_METERS)
                .withIdleMode(CANSparkMax.IdleMode.kBrake)
                .withRampRate(30)
        );
        extensionMotor.getEncoder().setPosition(RobotMap.telescopicArm.SHRINKED_LENGTH);

        extensionMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse,(float) RobotMap.telescopicArm.SHRINKED_LENGTH);
        extensionMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward,(float)RobotMap.telescopicArm.EXTENDED_LENGTH);


        angleMotor.config(new GBSparkMax.SparkMaxConfObject()
                .withPID(RobotMap.telescopicArm.ANGULAR_PID)
                .withPositionConversionFactor(RobotMap.General.Motors.SPARKMAX_TICKS_PER_RADIAN * RobotMap.telescopicArm.ANGULAR_GEAR_RATIO)
        );

        angleMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 0);
        angleMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float) (Math.PI/4));

        currentPosition = 0; //the start of the elevator is at pos 0, or we can get an absolute encoder.

    }
    public static TelescopicArm getInstance(){
        if(instance == null){
            instance = new TelescopicArm();
        }
        return instance;
    }

    public void stop(){
        extensionMotor.set(0);
    }

    public void rotateToAngle(double angleInDegrees) {
        if(angleInDegrees < 5 || angleInDegrees > 60) {
            targetAngle = currentPosition;
            //maybe led indicator?
            angleMotor.getPIDController().setReference(targetAngle, CANSparkMax.ControlType.kPosition);
            return;
        }

        targetAngle = angleInDegrees;
        angleMotor.getPIDController().setReference(targetAngle, CANSparkMax.ControlType.kPosition);
    }
    public void setLength (double length){
        if(length > RobotMap.telescopicArm.EXTENDED_LENGTH || length < RobotMap.telescopicArm.SHRINKED_LENGTH){
            targetLength = currentPosition;
            extensionMotor.getPIDController().setReference(targetAngle, CANSparkMax.ControlType.kPosition);
            //again maybe led indicator
            return;
        }
        targetLength = length;
        extensionMotor.getPIDController().setReference(targetLength, CANSparkMax.ControlType.kPosition);
    }


    public double getCurrentLength (){
        return extensionMotor.getEncoder().getPosition();
    }
    public double getArmAngle (){
        return angleMotor.getEncoder().getPosition();
    }




    public enum presetPositions{
        //height in meters
        //angle in radians
        coneHigh(1.545639026,49.1),
        coneMid(1.04560987,56.30993247),
        CubeHigh(1.352811886,41.70388403),
        CubeMid(83.45058418,34.59228869),
        low(0,5),
        ;
        public final double distance;
        public final double angleInDegrees;

        presetPositions(double distance,double angle) {
            this.distance = distance;
            this.angleInDegrees = angle;
        }
    }


}
