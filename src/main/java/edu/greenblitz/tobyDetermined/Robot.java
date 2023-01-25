package edu.greenblitz.tobyDetermined;



import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.greenblitz.tobyDetermined.commands.BatteryDisabler;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.IntakeObjSensor;
import edu.greenblitz.tobyDetermined.subsystems.Limelight;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.util.Color;

import java.awt.*;

public class Robot extends TimedRobot {
	private final Color kYellowTarget = new Color(0.364f, 0.522f, 0.107f);
	private final Color kPurpelTarget = new Color(0.2571f, 0.441f, 0.3f);
	private final Color kNothingTarget = new Color(0.284f, 0.481f, 0.234f);
	public final ColorMatch m_colorMatcher = new ColorMatch();
	private final ColorSensorV3 m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		Dashboard.init();
		Limelight.getInstance();
		initPortForwarding();
		LiveWindow.disableAllTelemetry();
		Battery.getInstance().setDefaultCommand(new BatteryDisabler());
		IntakeObjSensor.getInstance();
		
		//swerve
		
		SwerveChassis.getInstance().resetChassisPose();
		SwerveChassis.getInstance().resetAllEncoders();
		OI.getInstance();

		m_colorMatcher.addColorMatch(kYellowTarget);
		m_colorMatcher.addColorMatch(kPurpelTarget);
		m_colorMatcher.addColorMatch(kNothingTarget);
	}
	
	private static void initPortForwarding() {
		for (int port:RobotMap.Vision.portNumbers) {
			PortForwarder.add(port, "photonvision.local", port);
		}
	}
	
	
	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();

		/**
		 * The method GetColor() returns a normalized color value from the sensor and can be
		 * useful if outputting the color to an RGB LED or similar. To
		 * read the raw color, use GetRawColor().
		 *
		 * The color sensor works best when within a few inches from an object in
		 * well lit conditions (the built in LED is a big help here!). The farther
		 * an object is the more light from the surroundings will bleed into the
		 * measurements and make it difficult to accurately determine its color.
		 */
		Color detectedColor = m_colorSensor.getColor();

		/**
		 * Run the color match algorithm on our detected color
		 */
		String colorString;
		ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

		if (match.color == kYellowTarget) {
			colorString = "Yellow";
		} else if (match.color == kPurpelTarget){
			colorString = "Purpel";
		} else if (match.color == kNothingTarget){
			colorString = "Nothing";
		}else {
			colorString = "Unknown";
		}

		/**
		 * Open Smart Dashboard or Shuffleboard to see the color detected by the
		 * sensor.
		 */
		SmartDashboard.putNumber("Red", detectedColor.red);
		SmartDashboard.putNumber("Green", detectedColor.green);
		SmartDashboard.putNumber("Blue", detectedColor.blue);
		SmartDashboard.putNumber("Confidence", match.confidence);
		SmartDashboard.putString("Detected Color", colorString);
	}
	
	
	@Override
	public void disabledInit() {
		CommandScheduler.getInstance().cancelAll();
	}
	
	@Override
	public void teleopInit() {
		CommandScheduler.getInstance().cancelAll();
		SwerveChassis.getInstance().setIdleModeCoast();
	}
	
	@Override
	public void teleopPeriodic() {
	}
	
	
	/*
		TODO: Dear @Orel & @Tal, please for the love of god, use the very useful function: schedule(), this will help the code to actually work
	*/
	@Override
	public void autonomousInit() {
	
	}
	
	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}
	
	@Override
	public void testPeriodic() {
	}
}
