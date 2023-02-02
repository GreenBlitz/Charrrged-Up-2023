package edu.greenblitz.tobyDetermined;


import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import edu.greenblitz.tobyDetermined.subsystems.swerve.KazaSwerveModule;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SdsSwerveModule;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBFalcon;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class RobotMap {
	public static class General {
		public final static double minVoltageBattery = 11.97;
		public final static double VOLTAGE_COMP_VAL = 11.5;
		public final static double RAMP_RATE_VAL = 0.4;
		
		public static class Motors {
			public final static double SPARKMAX_TICKS_PER_RADIAN = Math.PI * 2;
			public final static double SPARKMAX_VELOCITY_UNITS_PER_RPM = 1;
			public static final double NEO_PHYSICAL_TICKS_TO_RADIANS = SPARKMAX_TICKS_PER_RADIAN / 42; //do not use unless you understand the meaning
			
			public final static double FALCON_TICKS_PER_RADIAN = 2 * Math.PI / 2048.0;
			public final static double FALCON_VELOCITY_UNITS_PER_RPM = 600.0 / 2048;
		}
	}

	public static class gyro { //Yum
		public static final int pigeonID = 12;
	}
	
	public static class Joystick {
		public static final int MAIN = 0;
		public static final int SECOND = 1;
	}
	
	public static class Pneumatics {
		public static class PCM {
			public static final int PCM_ID = 22;
			public static final PneumaticsModuleType PCM_TYPE = PneumaticsModuleType.CTREPCM;
		}
		
		public static class PressureSensor {
			public static final int PRESSURE = 3;
		}
	}
	
	public static class Vision {
		public static double standardDeviationOdometry = 0.001;
		public static double standardDeviationVision2d = 0.3;
		public static double standardDeviationVisionAngle = 0.1;
		public static int selectedTagId = 4;
		public static final int[] portNumbers = {5800,5801,5802,5803,5804,5805};
		public static final Pose3d apriltagLocation = new Pose3d(new Translation3d(5, 5, 0), new Rotation3d(0, 0, Math.PI));
		static List<AprilTag> apriltags = new ArrayList<>(5) ;
		static {
			apriltags.add(new AprilTag(selectedTagId,apriltagLocation));
		}
		public static final AprilTagFieldLayout aprilTagFieldLayout = new AprilTagFieldLayout(apriltags,10,10);
		public static final Transform3d RobotToCamera = new Transform3d(new Translation3d(), new Rotation3d());
		
	}
	
	public static class Swerve {
		static final Pose2d initialRobotPosition = new Pose2d(0, 0, new Rotation2d(0));
		public static final Translation2d[] SwerveLocationsInSwerveKinematicsCoordinates = new Translation2d[]{
				//the WPILib coordinate system is stupid. (x is forwards, y is leftwards)
				//the translations are given rotated by 90 degrees clockwise to avoid coordinates system conversion at output
				new Translation2d(0.3020647, 0.25265), /*fl*/
				new Translation2d(0.3020647, -0.25265),/*fr*/
				new Translation2d(-0.3020647, 0.25265),/*bl*/
				new Translation2d(-0.3020647, -0.25265)/*br*/};
		
		public static final double MAX_VELOCITY = 4.1818320981472068;
		public static final double MAX_ACCELERATION = 14.37979171376739;
		public static final double MAX_ANGULAR_SPEED = 10.454580245368017;
		
		
		public static final PIDObject rotationPID = new PIDObject().withKp(0.5).withKi(0).withKd(0).withFF(0.1);
		public static final double ks = 0.14876;
		public static final double kv = 3.3055;
		
		public static final double ka = 0.11023;
		
		public static KazaSwerveModule.KazaSwerveModuleConfigObject KazaModuleFrontLeft = new KazaSwerveModule.KazaSwerveModuleConfigObject(1, 10, 0, false); //front left
		
		public static KazaSwerveModule.KazaSwerveModuleConfigObject KazaModuleFrontRight = new KazaSwerveModule.KazaSwerveModuleConfigObject(3, 11, 2, true); //front right
		
		public static KazaSwerveModule.KazaSwerveModuleConfigObject KazaModuleBackLeft = new KazaSwerveModule.KazaSwerveModuleConfigObject(2, 8, 1, false); //back left
		
		public static KazaSwerveModule.KazaSwerveModuleConfigObject KazaModuleBackRight = new KazaSwerveModule.KazaSwerveModuleConfigObject(12, 5, 3, true); //back right
		
		public static SdsSwerveModule.SdsSwerveModuleConfigObject SdsModuleFrontLeft = new SdsSwerveModule.SdsSwerveModuleConfigObject(1, 0, 3, false, 3.4635 / (2 * Math.PI)); //front left
		
		public static SdsSwerveModule.SdsSwerveModuleConfigObject SdsModuleFrontRight = new SdsSwerveModule.SdsSwerveModuleConfigObject(3, 2, 1, true, 4.55 / (2 * Math.PI)); //front right
		
		public static SdsSwerveModule.SdsSwerveModuleConfigObject SdsModuleBackLeft = new SdsSwerveModule.SdsSwerveModuleConfigObject(5, 4, 2, false, 3.947 / (2 * Math.PI)); //back left
		
		public static SdsSwerveModule.SdsSwerveModuleConfigObject SdsModuleBackRight = new SdsSwerveModule.SdsSwerveModuleConfigObject(7, 6, 0, true, 5.386 / (2 * Math.PI)); //back right
		
		
		public static class KazaSwerve {
			public static final double ANG_GEAR_RATIO = 6.0;
			public static final double LIN_GEAR_RATIO = 8.0;
			
			
			public static final double WHEEL_CIRC = 0.0517 * 2 * Math.PI;
			public static final double linTicksToMeters = RobotMap.General.Motors.SPARKMAX_TICKS_PER_RADIAN * WHEEL_CIRC / LIN_GEAR_RATIO / (2 * Math.PI);
			public static final double angleTicksToWheelToRPM = RobotMap.General.Motors.SPARKMAX_VELOCITY_UNITS_PER_RPM / ANG_GEAR_RATIO;
			public static final double linTicksToMetersPerSecond = RobotMap.General.Motors.SPARKMAX_VELOCITY_UNITS_PER_RPM * WHEEL_CIRC / 60 / LIN_GEAR_RATIO;
			public static final PIDObject linPID = new PIDObject().withKp(0.0003).withMaxPower(0.5);
			public static final GBSparkMax.SparkMaxConfObject baseLinConfObj = new GBSparkMax.SparkMaxConfObject().withIdleMode(CANSparkMax.IdleMode.kBrake).withCurrentLimit(40).withRampRate(RobotMap.General.RAMP_RATE_VAL).withVoltageComp(RobotMap.General.VOLTAGE_COMP_VAL).withPID(linPID).withPositionConversionFactor(linTicksToMeters).withVelocityConversionFactor(linTicksToMetersPerSecond);
			public static final double angleTicksToRadians = RobotMap.General.Motors.SPARKMAX_TICKS_PER_RADIAN / ANG_GEAR_RATIO;
			public static final PIDObject angPID = new PIDObject().withKp(0.5).withMaxPower(1.0);
			public static final GBSparkMax.SparkMaxConfObject baseAngConfObj = new GBSparkMax.SparkMaxConfObject().withIdleMode(CANSparkMax.IdleMode.kBrake).withCurrentLimit(30).withRampRate(RobotMap.General.RAMP_RATE_VAL).withVoltageComp(RobotMap.General.VOLTAGE_COMP_VAL).withInverted(true).withPID(angPID).withPositionConversionFactor(angleTicksToRadians).withVelocityConversionFactor(angleTicksToWheelToRPM);
		}
		
		public static class SdsSwerve {
			public static final double ANG_GEAR_RATIO = (150.0 / 7);
			public static final double LIN_GEAR_RATIO = 8.14;
			
			public static final double WHEEL_CIRC = 0.0517 * 2 * Math.PI;
			public static final double linTicksToMeters = RobotMap.General.Motors.FALCON_TICKS_PER_RADIAN / 2 / Math.PI * WHEEL_CIRC / LIN_GEAR_RATIO;
			public static final double angleTicksToWheelToRPM = RobotMap.General.Motors.FALCON_VELOCITY_UNITS_PER_RPM / ANG_GEAR_RATIO;
			public static final double linTicksToMetersPerSecond = RobotMap.General.Motors.FALCON_VELOCITY_UNITS_PER_RPM / LIN_GEAR_RATIO * WHEEL_CIRC / 60;
			public static final double angleTicksToRadians = RobotMap.General.Motors.FALCON_TICKS_PER_RADIAN / ANG_GEAR_RATIO;
			public static final double magEncoderTicksToFalconTicks = 2 * Math.PI / angleTicksToRadians;
			
			public static final PIDObject angPID = new PIDObject().withKp(0.05).withMaxPower(1.0).withFF(0);//.withKd(10).withMaxPower(0.8);
			public static final GBFalcon.FalconConfObject baseAngConfObj = new GBFalcon.FalconConfObject().withNeutralMode(NeutralMode.Brake).withCurrentLimit(30).withRampRate(RobotMap.General.RAMP_RATE_VAL).withVoltageCompSaturation(RobotMap.General.VOLTAGE_COMP_VAL).withInverted(true).withPID(angPID);
			
			public static final PIDObject linPID = new PIDObject().withKp(0.0003).withMaxPower(0.5);
			public static final GBFalcon.FalconConfObject baseLinConfObj = new GBFalcon.FalconConfObject().withNeutralMode(NeutralMode.Brake).withCurrentLimit(40).withRampRate(RobotMap.General.RAMP_RATE_VAL).withVoltageCompSaturation(RobotMap.General.VOLTAGE_COMP_VAL).withPID(linPID);
		}

		public static class Autonomus {
			public static final TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(MAX_VELOCITY,MAX_ACCELERATION);
		}
		
	}
}