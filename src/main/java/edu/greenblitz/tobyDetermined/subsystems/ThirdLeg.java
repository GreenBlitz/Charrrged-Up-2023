package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ThirdLeg extends GBSubsystem {
    private static ThirdLeg instance;
    private final DoubleSolenoid solenoid;

    private ThirdLeg() {
        solenoid = new DoubleSolenoid(RobotMap.Pneumatics.PCM.PCM_ID, RobotMap.Pneumatics.PCM.PCM_TYPE, RobotMap.ThirdLeg.FORWARD_PORT, RobotMap.ThirdLeg.REVERSE_PORT);
    }

    public static ThirdLeg getInstance() {
        if(instance == null) {
            init();
            SmartDashboard.putBoolean("intake extender initialized via getinstance", true);
        }
        return instance;
    }

    public static void init(){
        instance = new ThirdLeg();
    }

    private void setValue(DoubleSolenoid.Value value) {
        solenoid.set(value);
    }

    public void extend() {
        setValue(DoubleSolenoid.Value.kForward);
    }

    public void retract() {
        setValue(DoubleSolenoid.Value.kReverse);
    }

    public boolean isExtended() {
        return solenoid.get().equals(DoubleSolenoid.Value.kForward);
    }

    public void toggleLeg() {
        if (isExtended()) {
            retract();
        } else {
            extend();
        }
    }

}
