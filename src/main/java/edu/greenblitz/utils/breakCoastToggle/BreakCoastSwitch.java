package edu.greenblitz.utils.breakCoastToggle;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.core.appender.rolling.action.IfAll;

import java.sql.Driver;
import java.util.HashMap;

public class BreakCoastSwitch extends GBSubsystem {

    private static BreakCoastSwitch instance;

    private boolean isDisabled;

    private static boolean lastState = false;
    private DigitalInput switchInput;
    private int switchPressCount;
    private HashMap<GBSubsystem, BreakCoastRunnables> subsystems;
    private Debouncer debouncer;

    public static BreakCoastSwitch getInstance() {
        if (instance == null) {
            instance = new BreakCoastSwitch(RobotMap.BreakCoastSwitch.BREAK_COAST_SWITCH_DIO_PORT);
        }
        return instance;
    }

    private BreakCoastSwitch(int id) {
        switchInput = new DigitalInput(id);
        subsystems = new HashMap<>();
        this.switchPressCount = 1;
        debouncer = new Debouncer(0.1);
        isDisabled = false;
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


    @Override
    public void periodic() {
        if (getSwitchState() != lastState && isDisabled) {
            lastState = !lastState;
            switchPressCount += lastState ? 1 : 0;
        }

        if(isDisabled){
            if (this.switchPressCount % 2 == 1) {
                setBreak();
            } else if (this.switchPressCount % 2 == 0) {
                setCoast();
            }
        }

    }

    public boolean getSwitchState() {
        return !debouncer.calculate(switchInput.get());
    }

    public void setIsDisabled (boolean isDisabled){
        this.isDisabled = isDisabled;
        if(isDisabled){
            switchPressCount = 1;
            setBreak();
        }
    }

}
