package edu.greenblitz.tobyDetermined.subsystems.intake;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Roller extends GBSubsystem {

    private static final int ROLLER_PORT_1 = 0;
    private static final int ROLLER_PORT_2 = 0;

    private final GBSparkMax roller1;
    private final GBSparkMax roller2;

    private static Roller instance;

    private Roller() {
        this.roller1 = new GBSparkMax(ROLLER_PORT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.roller2 = new GBSparkMax(ROLLER_PORT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        roller2.follow(roller1, true);
    }

    public static Roller getInstance() {
        if (instance == null) {
            instance = new Roller();
        }
        return instance;
    }

    public void setPower(double power) {
        if (Extender.getState() == Extender.ExtenderState.OPEN) {
           roller1.set(power);
        } else {
            roller1.set(0);
        }
    }
}
