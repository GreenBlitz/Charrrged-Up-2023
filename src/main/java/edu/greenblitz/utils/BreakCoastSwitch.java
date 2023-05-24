package edu.greenblitz.utils;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.HashMap;

public class BreakCoastSwitch {

    private static BreakCoastSwitch instance;
    private DigitalInput switchInput;
    private int switchPressCount;
    private HashMap<GBSubsystem, Runnable[]> subsystems;

    public static BreakCoastSwitch getInstance() {
        if (instance == null) {
            instance = new BreakCoastSwitch(RobotMap.BREAK_COAST_SWITCH_DIO_PORT);
        }
        return instance;
    }

    private BreakCoastSwitch(int id) {
        switchInput = new DigitalInput(id);
        subsystems = new HashMap<>();
        this.switchPressCount = 0;
    }

    public void addSubsystem(GBSubsystem subsystem, Runnable setBrakeMode, Runnable setCoastMode) {
        subsystems.putIfAbsent(subsystem, new Runnable[]{setBrakeMode, setCoastMode});
        subsystems.get(subsystem)[0].run(); //set the motors to break
    }


    public void setCoast() {
        for (Runnable[] subsystemToggle : subsystems.values()) {
            subsystemToggle[1].run();
        }
    }

    public void setBreak() {
        for (Runnable[] subsystemToggle : subsystems.values()) {
            subsystemToggle[0].run();
        }
    }


	private static boolean lastState = false;
	public void updateCoastBreak() { //run in disabled periodic
        if (isSwitchPressed() != lastState) {
			lastState = !lastState;
            switchPressCount++;
        }
	    SmartDashboard.putNumber("press count",switchPressCount);
        if (this.switchPressCount % 4 == 0) {
            setBreak();
        } else if (this.switchPressCount % 4 == 1) {
            setCoast();
        }
    }

    public boolean isSwitchPressed() {
        return !switchInput.get();
    }


}
