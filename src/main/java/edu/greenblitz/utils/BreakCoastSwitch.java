package edu.greenblitz.utils;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.wpilibj.DigitalInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class BreakCoastSwitch {

	private static BreakCoastSwitch instance;
	private DigitalInput switchInput;
	private boolean isBreak = true;
	private HashMap<GBSubsystem, Runnable[]> subsystems;

	public static BreakCoastSwitch getInstance(){
		if(instance == null){
			instance = new BreakCoastSwitch(RobotMap.BREAK_COAST_SWITCH_DIO_PORT);
		}
		return instance;
	}

	private BreakCoastSwitch (int id){
		switchInput = new DigitalInput(id);
		subsystems = new HashMap<>();
	}

	public void addSubsystem (GBSubsystem subsystem, Runnable setBrakeMode,Runnable setCoastMode){
		subsystems.putIfAbsent(subsystem, new Runnable[]{setBrakeMode,setCoastMode});
		subsystems.get(subsystem)[0].run(); //set the motors to break
	}


	public void setCoast (){
		for (Runnable[] subsystemToggle : subsystems.values()){
			subsystemToggle[1].run();
		}
	}

	public void setBreak (){
		for (Runnable[] subsystemToggle : subsystems.values()){
			subsystemToggle[0].run();
		}
	}


	public void toggleCoastBreak (){
		isBreak = switchInput.get();
		if(isBreak){
			setBreak();
		}else{
			setCoast();
		}
	}

	public boolean getSwitchState (){
		return switchInput.get();
	}


}
