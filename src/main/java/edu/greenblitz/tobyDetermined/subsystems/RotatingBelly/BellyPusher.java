package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class BellyPusher extends GBSubsystem {
    private DoubleSolenoid solenoid;

    private static BellyPusher instance;
    private PusherState state;

    private BellyPusher() {
        solenoid = new DoubleSolenoid(RobotMap.Pneumatics.PCM.PCM_ID, PneumaticsModuleType.CTREPCM, RobotMap.RotatingBelly.OPEN_PISTON_ID, RobotMap.RotatingBelly.CLOSE_PISTON_ID);
        updateState();
    }

    public static BellyPusher getInstance() {
        if (instance == null) {
            instance = new BellyPusher();
        }
        return instance;
    }

    public void openPiston() {
        solenoid.set(DoubleSolenoid.Value.kForward);
        updateState();
    }

    public void closePiston() {
        solenoid.set(DoubleSolenoid.Value.kReverse);
        updateState();
    }

    public void toggleState() {
        if (state == PusherState.CLOSED) {
            openPiston();
        } else {
            closePiston();
        }
    }

    private void updateState() {
        state = solenoid.get() == DoubleSolenoid.Value.kForward ? PusherState.OPEN : PusherState.CLOSED;
    }
    public PusherState getState(){
        return state;
    }

    public enum PusherState {
        OPEN, CLOSED;
    }


}
