package edu.greenblitz.tobyDetermined;



import com.revrobotics.ColorMatchResult;
import edu.greenblitz.tobyDetermined.commands.BatteryDisabler;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.IntakeObjSensor;
import edu.greenblitz.tobyDetermined.subsystems.Limelight;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.util.Color;

public class Robot extends TimedRobot {
//	private final Color kYellowTarget = new Color(0.364f, 0.522f, 0.107f);
//	private final Color kPurpelTarget = new Color(0.2571f, 0.441f, 0.3f);
//	private final Color kNothingTarget = new Color(0.284f, 0.481f, 0.234f);
//	public final ColorMatch m_colorMatcher = new ColorMatch();
//	private final ColorSensorV3 m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		Dashboard.init();
		Limelight.getInstance();
		initPortForwarding();
		LiveWindow.disableAllTelemetry();
		Battery.getInstance().setDefaultCommand(new BatteryDisabler());
		IntakeObjSensor.getInstance().periodic();
		
		//swerve
		
		SwerveChassis.getInstance().resetChassisPose();
		SwerveChassis.getInstance().resetAllEncoders();
		OI.getInstance();
	}
	
	private static void initPortForwarding() {
		for (int port:RobotMap.Vision.portNumbers) {
			PortForwarder.add(port, "photonvision.local", port);
		}
	}
	
	
	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
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
