package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class Shooter extends GBSubsystem {
    private CANSparkMax motor;
    private static Shooter instance;

    private Shooter() {
        motor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.setInverted(true);
        motor.setSmartCurrentLimit(24);
    }

    public static Shooter getInstance() {
        if (instance == null)
            instance = new Shooter();
        return instance;
    }

    public void setPower(double speed) {
        motor.set(speed);
    }

    public void stop() {
        motor.set(0);
    }
}