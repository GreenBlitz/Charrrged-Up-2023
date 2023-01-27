package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Claw extends GBSubsystem {
    private static Claw instance;
    private static final int FORWARD_PORT = 0;
    private static final int REVERSE_PORT = 0;
    private final DoubleSolenoid claw;
    private ClawState state = ClawState.CLOSED;
    public enum ClawState {
        OPEN, CLOSED
    }

    private Claw(){
        claw = new DoubleSolenoid(RobotMap.Pneumatics.PCM.PCM_ID, RobotMap.Pneumatics.PCM.PCM_TYPE, FORWARD_PORT, REVERSE_PORT);
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

    public void open() {
        claw.set(DoubleSolenoid.Value.kForward);
        this.state = ClawState.OPEN;
    }

    public void close() {
        claw.set(DoubleSolenoid.Value.kReverse);
        this.state = ClawState.CLOSED;
    }
    }


