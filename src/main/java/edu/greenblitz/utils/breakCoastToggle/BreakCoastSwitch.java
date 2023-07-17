package edu.greenblitz.utils.breakCoastToggle;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.HashMap;

public class BreakCoastSwitch {

    private static BreakCoastSwitch instance;

    private static boolean lastState = false;
    private DigitalInput switchInput;
    private int switchPressCount;
    private HashMap<GBSubsystem, BreakCoastRunnables> subsystems;

    public static BreakCoastSwitch getInstance() {
        if (instance == null) {
            instance = new BreakCoastSwitch(RobotMap.BreakCoastSwitch.BREAK_COAST_SWITCH_DIO_PORT);
        }
        return instance;
    }

    private BreakCoastSwitch(int id) {
        switchInput = new DigitalInput(id);
        subsystems = new HashMap<>();
        this.switchPressCount = 0;
    }

    public void addSubsystem(GBSubsystem subsystem, Runnable setBrakeMode, Runnable setCoastMode) {
        subsystems.putIfAbsent(subsystem, new BreakCoastRunnables(setBrakeMode, setCoastMode));
        setBrakeMode.run(); //set the motors to break
    }


    public void setCoast() {
        for (BreakCoastRunnables subsystemToggle : subsystems.values()) {
            subsystemToggle.getCoastRunnable().run();
        }
    }

    public void setBreak() {
        for (BreakCoastRunnables subsystemToggle : subsystems.values()) {
            subsystemToggle.getBreakRunnable().run();
        }
    }

    public void toggleBreakCoast() {
        if (getSwitchState() != lastState) {
            switchPressCount += lastState ? 1 : 0;
            lastState = !lastState;
        }

        if (this.switchPressCount % 2 == 1) {
            setBreak();
        } else if (this.switchPressCount % 2 == 0) {
            setCoast();
        }

        SmartDashboard.putNumber("press count", switchPressCount);
    }


    public boolean getSwitchState() {
        return !switchInput.get();
    }


}
