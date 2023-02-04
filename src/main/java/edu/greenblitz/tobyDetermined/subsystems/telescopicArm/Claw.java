package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.motors.GBSparkMax;

public class Claw extends GBSubsystem {
    private static Claw instance;
    private static final double MOTOR_POWER_GRIP = 0.3;
    private static final double MOTOR_POWER_RELEASE = -0.3;
    private GBSparkMax motor;

    private Claw(){
        motor = new GBSparkMax(RobotMap.telescopicArm.claw.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public static Claw getInstance() {
        if (instance == null){
            instance = new Claw();
        }
        return instance;
    }

    public void grip() {
        motor.set(MOTOR_POWER_GRIP);
    }

    public void eject() {
        motor.set(MOTOR_POWER_RELEASE);
    }

    public void stopMotor (){
        motor.set(0);
    }
}
