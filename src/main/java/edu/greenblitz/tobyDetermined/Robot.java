package edu.greenblitz.tobyDetermined;



import edu.greenblitz.tobyDetermined.commands.BatteryDisabler;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.IntakeGameObjectSensor;
import edu.greenblitz.tobyDetermined.subsystems.Limelight;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		Dashboard.init();
		Limelight.getInstance();
		initPortForwarding();
		LiveWindow.disableAllTelemetry();
		Battery.getInstance().setDefaultCommand(new BatteryDisabler());
//		IntakeObjectSensor.getInstance().periodic();

		//swerve

		SwerveChassis.getInstance().resetChassisPose();
		SwerveChassis.getInstance().resetAllEncoders();
		OI.getInstance();
		ChassisSpeeds chassisSpeeds = new ChassisSpeeds(1,1,1);
		ChassisSpeeds chassisSpeeds1 = new ChassisSpeeds(1,1,-1);
		Pose2d robot_pose_vel = new Pose2d(chassisSpeeds.vxMetersPerSecond * RobotMap.General.ITERATION_DT,
				chassisSpeeds.vyMetersPerSecond * RobotMap.General.ITERATION_DT,
				Rotation2d.fromRadians(chassisSpeeds.omegaRadiansPerSecond * RobotMap.General.ITERATION_DT));
		Twist2d twist_vel = new Pose2d().log(robot_pose_vel);
		ChassisSpeeds updated_chassis_speeds = new ChassisSpeeds(
				twist_vel.dx / RobotMap.General.ITERATION_DT, twist_vel.dy / RobotMap.General.ITERATION_DT, twist_vel.dtheta / RobotMap.General.ITERATION_DT);
		Pose2d robot_pose_vel1 = new Pose2d(chassisSpeeds1.vxMetersPerSecond * RobotMap.General.ITERATION_DT,
				chassisSpeeds1.vyMetersPerSecond * RobotMap.General.ITERATION_DT,
				Rotation2d.fromRadians(chassisSpeeds1.omegaRadiansPerSecond * RobotMap.General.ITERATION_DT));
		Twist2d twist_vel1 = new Pose2d().log(robot_pose_vel1);
		ChassisSpeeds updated_chassis_speeds1 = new ChassisSpeeds(
				twist_vel1.dx / RobotMap.General.ITERATION_DT, twist_vel1.dy / RobotMap.General.ITERATION_DT, twist_vel1.dtheta / RobotMap.General.ITERATION_DT);
		SmartDashboard.putNumber("x val", updated_chassis_speeds.vxMetersPerSecond);
		SmartDashboard.putNumber("x val1", updated_chassis_speeds1.vxMetersPerSecond);
		SmartDashboard.putNumber("y val", updated_chassis_speeds.vyMetersPerSecond);
		SmartDashboard.putNumber("y val1", updated_chassis_speeds1.vyMetersPerSecond);

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
		SwerveChassis.getInstance().setIdleModeBrake();
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
