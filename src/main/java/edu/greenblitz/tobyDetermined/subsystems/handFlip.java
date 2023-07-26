package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.utils.motors.GBSparkMax;

public class handFlip extends GBSubsystem{

    private handFlip instance;
    private GBSparkMax motor;

    private handFlip() {
        motor = new GBSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    }
    public handFlip getInstance() {
        if(instance == null) {
            instance = new handFlip();
        }
        return instance;
    }

    public void setSpeed(double speed)
    {
        this.motor.set(speed);
    }
}
