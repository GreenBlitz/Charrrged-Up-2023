package edu.greenblitz.tobyDetermined.subsystems.intake;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.motors.GBSparkMax;

public class IntakeRoller extends GBSubsystem {
	public static IntakeRoller instance;
	private final GBSparkMax motor;
	private IntakeRoller(){
		motor = new GBSparkMax(RobotMap.Intake.ROLLER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.config(
				new GBSparkMax.SparkMaxConfObject()
						.withCurrentLimit(RobotMap.Intake.CUR_LIMIT)
						.withInverted(RobotMap.Intake.INVERTED)
						.withRampRate(RobotMap.Intake.RAMP_RATE)
		);
	}
	
	public IntakeRoller getInstance(){
		if(instance != null) {instance = new IntakeRoller();}
		return instance;
	}
	
	public void roll(double power){
		motor.set(power);
	}
	
	public void rollIn(){
		roll(RobotMap.Intake.DEFAULT_POWER);
	}
	
	public void rollOut(){
		roll(-RobotMap.Intake.DEFAULT_POWER);
	}
	
	public void rollInside(){
		roll(RobotMap.Intake.ROLL_INSIDE_POWER);
	}
	
	public void stop(){
		roll(0);
	}
}
