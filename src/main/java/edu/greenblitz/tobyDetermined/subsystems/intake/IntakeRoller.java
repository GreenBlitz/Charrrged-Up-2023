package edu.greenblitz.tobyDetermined.subsystems.intake;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeRoller extends GBSubsystem {
	private static IntakeRoller instance;
	private final GBSparkMax motor;

	private IntakeRoller() {
		motor = new GBSparkMax(RobotMap.Intake.ROLLER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.config(
				new GBSparkMax.SparkMaxConfObject()
						.withCurrentLimit(RobotMap.Intake.CURRENT_LIMIT)
						.withInverted(RobotMap.Intake.INVERTED)
						.withRampRate(RobotMap.Intake.RAMP_RATE)
		);
	}

	public static IntakeRoller getInstance() {
		if(instance == null) {
			init();
			SmartDashboard.putBoolean("intake roller initialized via getinstance", true);
		}
		return instance;
	}

	public static void init(){
		instance = new IntakeRoller();
	}

	public void roll(double power) {
		motor.set(power);
	}

	public void rollIn() {
		roll(RobotMap.Intake.DEFAULT_POWER);
	}

	public void rollOut() {
		roll(-RobotMap.Intake.DEFAULT_POWER);
	}

	public void rollInside() {
		roll(RobotMap.Intake.ROLL_INSIDE_POWER);
	} //into the robot

	public void stop() {
		roll(0);
	}
}
