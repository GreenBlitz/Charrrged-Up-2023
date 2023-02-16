package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Map;

public class Dashboard extends GBSubsystem {

	private static Dashboard instance;

	public static Dashboard getInstance() {
		if (instance == null) {
			init();
			SmartDashboard.putBoolean("dashboard initialized via getinstance", true);
		}
		return instance;
	}

	public static void init() {
		instance = new Dashboard();
	}

	private Dashboard() {
		driversDashboard();
		swerveDashboard();
	}

	@Override
	public void periodic() {

//        SmartDashboard.putNumber("FR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
//        SmartDashboard.putNumber("FL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
//        SmartDashboard.putNumber("BR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
//        SmartDashboard.putNumber("BL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));
//        SmartDashboard.putNumber("y", SwerveChassis.getInstance().getRobotPose().getY());
//        SmartDashboard.putNumber("x", SwerveChassis.getInstance().getRobotPose().getX());
//        SmartDashboard.putNumber("angle", SwerveChassis.getInstance().getRobotPose().getRotation().getDegrees());
//        SmartDashboard.putNumber("pigeon angle", Units.radiansToDegrees(SwerveChassis.getInstance().getPigeonGyro().getYaw()));
//
//        SmartDashboard.putNumber("chassis speed x", SwerveChassis.getInstance().getChassisSpeeds().vxMetersPerSecond);
//        SmartDashboard.putNumber("FR-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_RIGHT));
//        SmartDashboard.putNumber("FL-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_LEFT));
//        SmartDashboard.putNumber("BR-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_RIGHT));
//        SmartDashboard.putNumber("BL-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_LEFT));
//
//        SmartDashboard.putString("pose", SwerveChassis.getInstance().getRobotPose().toString());
//
//		SmartDashboard.putNumber("grid pos id", Grid.getInstance().getSelectedPositionID());
//		SmartDashboard.putString("grid pos", Grid.getInstance().getSelectedPosition().toString());

	}


	public void driversDashboard() {
		ShuffleboardTab driversTab = Shuffleboard.getTab("Drivers");

		//arm states
		ShuffleboardLayout armStateWidget = driversTab.getLayout("Arm states", BuiltInLayouts.kGrid)
				.withPosition(0, 0).withSize(2, 2).withProperties(Map.of("Label position", "TOP", "Number of columns", 2, "Number of rows", 2));
		armStateWidget.addString("Extender State", () -> Extender.getInstance().getState().toString()).withPosition(0, 0);
		armStateWidget.addDouble("Length", () -> Extender.getInstance().getLength()).withPosition(1, 0);
		armStateWidget.addString("Elbow State", () -> Elbow.getInstance().getState().toString()).withPosition(0, 1);
		armStateWidget.addDouble("Angle", () -> Elbow.getInstance().getAngle()).withPosition(1, 1);

		//arm state
		driversTab.addString("Arm state", ()-> "doesn't exist").withPosition(4,2).withSize(1,2);

		//grid todo make it mirror by alliance
		ShuffleboardLayout grid = driversTab.getLayout("Grid", BuiltInLayouts.kGrid)
				.withPosition(2, 0).withSize(6, 2).withProperties(Map.of("Label position", "TOP", "Number of columns", 9, "Number of rows", 3));
		for (int i = 0; i < 9; i++) {
			for (Grid.Height height : Grid.Height.values()) {
				int finalI = i;
				int finalHeight = height.ordinal();
				grid.addBoolean(i+1 + " " + height, () -> (Grid.getInstance().getSelectedHeightID() == finalHeight && Grid.getInstance().getSelectedPositionID() == finalI))
						.withPosition(finalI,2 - finalHeight);
			}
		}

		//pose
		ShuffleboardLayout robotPoseWidget = driversTab.getLayout("Robot pose", BuiltInLayouts.kList)
				.withPosition(0, 2).withSize(1, 2).withProperties(Map.of("Label position", "TOP"));
		robotPoseWidget.addDouble("X", () -> SwerveChassis.getInstance().getRobotPose().getX());
		robotPoseWidget.addDouble("Y", () -> SwerveChassis.getInstance().getRobotPose().getY());
		robotPoseWidget.addDouble("Rotation", () -> SwerveChassis.getInstance().getRobotPose().getRotation().getDegrees());

		//battery
		driversTab.addDouble("Battery", () -> Battery.getInstance().getCurrentVoltage())
				.withPosition(9, 3);

		//object inside
		driversTab.addString("Object inside", /*()->RotatingBelly.getInstance().getGameObject().toString()*/()-> "robot does not exist")
				.withSize(1,1).withPosition(2,2);

		//field
		driversTab.add("Field", SwerveChassis.getInstance().getField()).withPosition(5,2).withSize(3,2);

		//console
		ShuffleboardLayout console = driversTab.getLayout("Console", BuiltInLayouts.kList)
				.withPosition(8,0).withSize(2,3).withProperties(Map.of("Label position", "TOP"));
		//todo write console and update it, maybe as sendable;

		//ready to place
		driversTab.addBoolean("Ready to place", ()->false).withPosition(3,2).withSize(1,2);
		//todo check if at place and arm in pos


	}

	public void swerveDashboard() {
		ShuffleboardTab swerveTab = Shuffleboard.getTab("Swerve");
		for (SwerveChassis.Module module : SwerveChassis.Module.values()) {
			swerveTab.addDouble(module + "-angle", () -> Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(module)), 360))
					.withSize(2, 1).withPosition(module.ordinal() * 2, 0);
			swerveTab.addDouble(module + "-absolute-angle", () -> SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(module))
					.withSize(2, 1).withPosition(module.ordinal() * 2, 1);
		}
		swerveTab.addDouble("pigeon-angle", () -> Math.toDegrees(SwerveChassis.getInstance().getChassisAngle()))
				.withSize(1, 1).withPosition(0, 2);

	}
}
