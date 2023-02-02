package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.motors.GBSparkMax;

public class Claw extends GBSubsystem {
    private static Claw instance;
    private static final int MOTOR_PORT = 0;
    private static final double motorPowerGrip = 0.3;
    private static final double motorPowerRelease = -0.3;
    private GBSparkMax motor;
    private ClawState state = ClawState.CLOSED;
    public enum ClawState {
        OPEN, CLOSED
    }

    private Claw(){
        motor = new GBSparkMax(MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public static Claw getInstance() {
        if (instance == null){
            instance = new Claw();
        }
        return instance;
    }

    public static ClawState getState() {
        return instance.state;
    }

    public void grip() {
        motor.set(motorPowerGrip);
        this.state = ClawState.OPEN;
    }

    public void eject() {
        motor.set(motorPowerRelease);
        this.state = ClawState.CLOSED;
    }

    public void stopMotor (){
        motor.set(0);
    }
}
