package edu.greenblitz.tobyDetermined.subsystems.intake;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Extender extends GBSubsystem {
    private static final int FORWARD_PORT = 0;
    private static final int REVERSE_PORT = 0;
    private final DoubleSolenoid extender;
    private ExtenderState state = ExtenderState.CLOSED;
    private static Extender instance;

    public enum ExtenderState {
        OPEN, CLOSED
    }

    private Extender() {
        extender = new DoubleSolenoid(RobotMap.Pneumatics.PCM.PCM_ID, RobotMap.Pneumatics.PCM.PCM_TYPE, FORWARD_PORT, REVERSE_PORT);
    }

    public static Extender getInstance(){
        if (instance == null){
            instance = new Extender();
        }
        return instance;
    }

    public static ExtenderState getState() {
        return instance.state;
    }


    //opt 1
    public void setState(ExtenderState state) {
        this.state = state;
        switch (state) {
            case OPEN:
                extender.set(DoubleSolenoid.Value.kForward);
                break;
            case CLOSED:
                extender.set(DoubleSolenoid.Value.kReverse);
                break;
        }
    }



    //opt 2
    public void extend() {
        extender.set(DoubleSolenoid.Value.kForward);
        this.state = ExtenderState.OPEN;
    }

    public void retract() {
        extender.set(DoubleSolenoid.Value.kReverse);
        this.state = ExtenderState.CLOSED;
    }
}