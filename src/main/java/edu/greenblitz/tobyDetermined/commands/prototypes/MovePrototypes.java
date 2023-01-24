package edu.greenblitz.tobyDetermined.commands.prototypes;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.subsystems.Prototypes;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

public class MovePrototypes extends GBCommand {

	private int numOfMotors;
	private ArrayList<SendableChooser<String>> motors;
	public MovePrototypes(int numOfMotors, ArrayList<SendableChooser<String>> motors){
		this.numOfMotors =numOfMotors;
		this.motors = motors;
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
		super.execute();
		for (int i = 0; i < numOfMotors; i++) {
			double power = SmartDashboard.getNumber("power" + i, 0);
			if(SmartDashboard.getNumber("power all", -2) != -2){
				power = SmartDashboard.getNumber("power all", 0);
			}
			if (!(OI.getInstance().getMainJoystick().A.getAsBoolean() || OI.getInstance().getMainJoystick().B.getAsBoolean())){
				power = 0;
			}
			else{
				power *= OI.getInstance().getMainJoystick().B.getAsBoolean() ? -1 : 1;

			}
			switch(motors.get(i).getSelected()){
				case "sparkmax":
					Prototypes.getSparkMax((int)SmartDashboard.getNumber("id" + i, 0)).set(power);
					break;
				case "talon":
					Prototypes.getTalon((int)SmartDashboard.getNumber("id" + i, 0)).set(TalonSRXControlMode.PercentOutput, power);
					break;

			}
		}

	}
	
	public static ArrayList<SendableChooser<String>> initPrototypes(int numOfMotors){
		ArrayList<SendableChooser<String>> motors = new ArrayList<SendableChooser<String>>(numOfMotors);
		for (int i = 0; i < numOfMotors; i++) {
			motors.add(new SendableChooser<String>());
			motors.get(i).setDefaultOption("sparkmax", "sparkmax");
			motors.get(i).addOption("talon", "talon");
			SmartDashboard.putData("motor" + i,motors.get(i));
			SmartDashboard.putNumber("id" + i, i);
			SmartDashboard.putNumber("power" + i, 0);
		}
		SmartDashboard.putNumber("power all", -2);
		return motors;
	}
}
