package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.SparkMaxAbsoluteEncoder;
import edu.greenblitz.tobyDetermined.Field;
import edu.greenblitz.tobyDetermined.IsRobotReady;
import edu.greenblitz.tobyDetermined.Nodesssss.CurrentNodeArm;
import edu.greenblitz.tobyDetermined.commands.NodeToNeighbourSupplier;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.subsystems.Limelight.MultiLimelight;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.PitchRollAdder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;

import java.util.Map;

public class Dashboard extends GBSubsystem {

	private static Dashboard instance;

	public MechanismLigament2d armWidget;

	public static Dashboard getInstance() {
		init();
		return instance;
	}

	public static void init() {
		if (instance == null) {
			instance = new Dashboard();
		}
	}

	private Dashboard() {
		openDriversDashboard();
		swerveDashboard();
		armDashboard();
		pigeonDashboard();
	}

	private boolean driverDashboardInitiated = false;
	/**
	 *  activate on robot-init
	 * */
	public void openDriversDashboard(){
		Shuffleboard.getTab("Drivers");
	}

	/**
	 *  activate not on robot-init, has to be activated in auto-init or teleop-init
	 * */
	public void activateDriversDashboard() {
		if (driverDashboardInitiated) return;

		driverDashboardInitiated = true;
		ShuffleboardTab driversTab = Shuffleboard.getTab("Drivers");

		//arm states
		ShuffleboardLayout armStateWidget = driversTab.getLayout("Arm states drivers", BuiltInLayouts.kGrid)
				.withPosition(0, 0).withSize(2, 2).withProperties(Map.of("Label position", "TOP", "Number of columns", 2, "Number of rows", 2));
		armStateWidget.addString("Extender State drivers", () -> Extender.getInstance().getState().toString()).withPosition(0, 0);
		armStateWidget.addDouble("Length drivers", () -> Extender.getInstance().getLength()).withPosition(1, 0);
		armStateWidget.addString("Elbow State drivers", () -> Elbow.getInstance().getState().toString()).withPosition(0, 1);
		armStateWidget.addDouble("Angle drivers", () -> (Elbow.getInstance().getAngleRadians())).withPosition(1, 1);
		//arm state
		driversTab.addString("Arm state", () -> "doesn't exist").withPosition(4, 2).withSize(1, 2);

		//grid todo make it mirror by alliance
		ShuffleboardLayout grid = driversTab.getLayout("Grid", BuiltInLayouts.kGrid)
				.withPosition(2, 0).withSize(6, 2).withProperties(Map.of("Label position", "TOP", "Number of columns", 9, "Number of rows", 3));

		boolean isRedAlliance = DriverStation.getAlliance() == DriverStation.Alliance.Red;
		for (int i = 0; i < Field.PlacementLocations.getLocationsOnRedSide().length; i++) {
			for (Grid.Height height : Grid.Height.values()) {
				int finalGridPositionID = i;
				int finalHeight = height.ordinal();
				grid.addBoolean(i + 1 + " " + height, () ->
								(Grid.getInstance().getSelectedHeightID() == finalHeight && Grid.getInstance().getSelectedPositionID() == finalGridPositionID))
						.withPosition(isRedAlliance? finalGridPositionID: 8-finalGridPositionID , 2 - finalHeight);
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

		//selected object
		driversTab.addBoolean("cone???", ObjectSelector::IsCone)
				.withSize(1, 1).withPosition(2, 2);

		//field
		driversTab.add("Field", SwerveChassis.getInstance().getField()).withPosition(5, 2).withSize(3, 2);
		
		driversTab.add("elbow ang", Elbow.getInstance().getAngleRadians());
		
		
		driversTab.add("limelight NT", MultiLimelight.getInstance().isConnected());




		//console
		ShuffleboardLayout console = Console.getShuffleboardConsole(driversTab)
				.withPosition(8, 0).withSize(2, 3).withProperties(Map.of("Label position", "TOP"));


		//ready to place
		driversTab.addBoolean("Ready to place", IsRobotReady::isRobotReady).withPosition(3, 2).withSize(1, 2);
		//todo check if at place and arm in pos
	}

	PIDController extenderController = new PIDController(Extender.getInstance().getPID().getKp(), Extender.getInstance().getPID().getKi(), Extender.getInstance().getPID().getKd());
	PIDController elbowController = new PIDController(Elbow.getInstance().getPID().getKp(), Elbow.getInstance().getPID().getKi(), Elbow.getInstance().getPID().getKd());

	public void armDashboard() {
		ShuffleboardTab armTab = Shuffleboard.getTab("Arm debug");
		//arm states
		ShuffleboardLayout armStateWidget = armTab.getLayout("Arm states", BuiltInLayouts.kGrid)
				.withPosition(0, 0).withSize(2, 2).withProperties(Map.of("Label position", "TOP", "Number of columns", 2, "Number of rows", 2));


		armStateWidget.addString("Extender State", () -> Extender.getInstance().getState().toString()).withPosition(0, 0);
		armStateWidget.addDouble("Length", () -> Extender.getInstance().getLength()).withPosition(1, 0);
		armStateWidget.addString("Elbow State", () -> Elbow.getInstance().getState().toString()).withPosition(0, 1);
		armStateWidget.addDouble("Angle", () -> Elbow.getInstance().getAngleRadians()).withPosition(1, 1);

		armTab.addDouble("elbow error", () -> Elbow.getInstance().getGoalAngle() - Elbow.getInstance().getAngleRadians());
		armTab.addDouble("extender error", () -> Extender.getInstance().getGoalLength() - Extender.getInstance().getLength());
		//arm state
		armTab.addString("Arm state", () -> "exists").withPosition(4, 2).withSize(1, 2);

		armTab.addBoolean("beam passes", () -> Extender.getInstance().getLimitSwitch());

		//arm ff
		armTab.addDouble("elbow ff", ()-> Elbow.getInstance().getDebugLastFF());
		armTab.addDouble("encoder value", ()-> Elbow.getInstance().motor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle).getPosition()).withPosition(0, 0).withSize(2, 2);
		Mechanism2d mech = new Mechanism2d(3, 3);

		armTab.addDouble("extender velocity",()-> Extender.getInstance().getVelocity());
		armTab.addDouble("angular velocity", () -> Elbow.getInstance().getVelocity());
		armTab.addString("Current Node ", () -> CurrentNodeArm.getInstance().getCurrentNode().toString());
		armTab.addString("wantedNode" , ()-> NodeToNeighbourSupplier.getEnd().toString());
		MechanismRoot2d root = mech.getRoot("arm root", 1.5, 1.5);

		armWidget = root.append(new MechanismLigament2d("arm", 30, 270));
		armTab.add("arm mechanism", mech);
		armTab.add("extenderPID", extenderController);
		armTab.add("elbowPID", elbowController);
	}

	public void swerveDashboard() {
		ShuffleboardTab swerveTab = Shuffleboard.getTab("Swerve");
		for (SwerveChassis.Module module : SwerveChassis.Module.values()) {
			swerveTab.addDouble(module + "-angle", () -> Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(module)), 360))
					.withSize(2, 1).withPosition(module.ordinal() * 2, 0);
			swerveTab.addDouble(module + "-absolute-angle", () -> SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(module))
					.withSize(2, 1).withPosition(module.ordinal() * 2, 1);
			swerveTab.addDouble(module + "-lin-dist", () -> SwerveChassis.getInstance().getSwerveModulePositions()[module.ordinal()].distanceMeters)
					.withSize(2, 1).withPosition(module.ordinal() * 2, 2);
		}
		swerveTab.addDouble("pigeon-angle", () -> Math.toDegrees(SwerveChassis.getInstance().getChassisAngle()))
				.withSize(1, 1).withPosition(0, 3);

	}

	public void pigeonDashboard(){
		ShuffleboardTab pigeonTab = Shuffleboard.getTab("pigeon debug");
		ShuffleboardLayout pigeonWidget = pigeonTab.getLayout("pigeon angles", BuiltInLayouts.kGrid)
				.withPosition(0, 0).withSize(2, 2).withProperties(Map.of("Label position", "TOP", "Number of columns", 2, "Number of rows", 2));

		pigeonWidget.addDouble("pitch (irl roll)", () -> SwerveChassis.getInstance().getPigeonGyro().getPitch());
		pigeonWidget.addDouble("roll (irl pitch)", () -> Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getRoll()));
		pigeonWidget.addDouble("pitch roll add ", () -> PitchRollAdder.add(SwerveChassis.getInstance().getPigeonGyro().getPitch(),SwerveChassis.getInstance().getPigeonGyro().getRoll()));
	}

	public PIDObject getElbowPID() {
		return new PIDObject().withKp(elbowController.getP()).withKi(elbowController.getI()).withKd(elbowController.getD());
	}

	public PIDObject getExtenderPID() {
		return new PIDObject().withKp(extenderController.getP()).withKi(extenderController.getI()).withKd(extenderController.getD());
	}

}
