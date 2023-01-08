package edu.greenblitz.pegasus.utils;

import edu.greenblitz.pegasus.subsystems.GBSubsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.*;

/**
 * able to handle more that one at a time
 */
public class VisionMaster extends GBSubsystem {

	private static final long MAX_HANDSHAKE_TIME = 3000;
	private static VisionMaster instance;
	private final NetworkTable visionTable;
	private final NetworkTableEntry algorithm;
	private final NetworkTableEntry output;
	private final NetworkTableEntry found;
	private final NetworkTableEntry gameState;
	private final NetworkTableEntry shifterState;
	private final NetworkTableEntry handshake;
	private final Logger logger;
	private long lastPrintTime = 0;
	private VisionLocation current;
	private Algorithm currentAlgorithm;
	private GameState currentGameState;
	private boolean visionGood = false;
	private long lastHandShake;

	private VisionMaster() {
		super();
		logger = LogManager.getLogger(getClass());
		visionTable = NetworkTableInstance.getDefault().getTable("vision"); // table

		algorithm = visionTable.getEntry("algorithm"); // our output, tells the handshake what algorithm to run
		output = visionTable.getEntry("output"); // our input, the output of the vision (usually a double array of size 3)
		found = visionTable.getEntry("found"); // our input, boolean indicating if the last sent data was valid
		gameState = visionTable.getEntry("game_state");
		shifterState = visionTable.getEntry("shifter_state");
		handshake = visionTable.getEntry("handshake");
	}

	public static VisionMaster getInstance() {
		if (instance == null) {
			instance = new VisionMaster();
		}
		return instance;
	}

	private void setCurrentGameState(GameState state) {
		this.currentGameState = state;
	}

	public Algorithm getCurrentAlgorithm() {
		return currentAlgorithm;
	}

	private void setCurrentAlgorithm(Algorithm algo) {
		this.currentAlgorithm = algo;
		UARTCommunication.getInstance().setAlgo(algo);
	}

	public boolean isLastDataValid() {
		if (UARTCommunication.getInstance().getBoolean("uart connection good", false) && UARTCommunication.getInstance().connectionActive() && visionGood) {
			return true;
		}
		if (System.currentTimeMillis() - lastHandShake > MAX_HANDSHAKE_TIME) {
			putString("vision Error", "Vision took to long to respond");
			return false;
		}
		return found.getBoolean(false);
	}

	public double[] getCurrentRawVisionData() {
		VisionLocation data = UARTCommunication.getInstance().get();
		if (data == null) {
			if (output.getType() != NetworkTableType.kDoubleArray) {
				putString("vision Error", "Vision sent data that isn't a double array");
				visionGood = false;
				return null;
			}
			visionGood = true;
			putString("vision Error", "None");
			return output.getValue().getDoubleArray();
		}
		visionGood = true;
		return data.toDoubleArray();
	}

	public VisionLocation getVisionLocation() {
		if (current == null) {
			current = getVisionLocationInternal();
		}
		return current;
	}

	private VisionLocation getVisionLocationInternal() {
		VisionLocation loc;
		if (UARTCommunication.getInstance().connectionActive()) {
			loc = UARTCommunication.getInstance().get();
		} else {
			double[] input = getCurrentRawVisionData();

			if (input == null) return new VisionLocation(new double[]{Double.NaN, Double.NaN, Double.NaN});
			return new VisionLocation(input);
		}

		if (loc == null) return new VisionLocation(new double[]{Double.NaN, Double.NaN, Double.NaN});

		return loc;
	}

	@Override
	public void periodic() {
		long t0 = System.currentTimeMillis();
		current = getVisionLocationInternal();
		putNumber("Time to get data", System.currentTimeMillis() - t0);

		if (System.currentTimeMillis() - lastPrintTime > 500) {
			putString("Vision Location: ", current.toString());
			putNumber("Vision Full Distance: ", current.getFullDistance());
			lastPrintTime = System.currentTimeMillis();
		}
		if (currentAlgorithm != null) {
			putString("vision algo", currentAlgorithm.getRawName());
		}
		if (handshake.getBoolean(false)) {
			lastHandShake = System.currentTimeMillis();
			handshake.setBoolean(false);
		}
		putString("vision raw data", current.toString());
		putNumber("vision planery distance", current.getPlaneDistance());
		putNumber("vision derived angle", current.getRelativeAngle());
		putBoolean("vision valid", isLastDataValid());
		putNumber("vision full distance", current.getFullDistance());
	}

	public enum Algorithm {
		POWER_CELLS("power_cells"),
		HEXAGON("hexagon"),
		FEEDING_STATION("feeding_station"),
		ROULETTE("roulette");

		public final String rawAlgorithmName;

		Algorithm(String rawAlgorithmName) {
			this.rawAlgorithmName = rawAlgorithmName;
		}

		public String getRawName() {
			return rawAlgorithmName;
		}

		public void setAsCurrent() {
			VisionMaster.getInstance().setCurrentAlgorithm(this);
		}
	}

	public enum GameState {
		DISABLED("disabled"),
		AUTONOMOUS("auto"),
		TELEOP("teleop");


		public final String rawStateName;

		GameState(String rawStateName) {
			this.rawStateName = rawStateName;
		}

		public void setAsCurrent() {
			VisionMaster.getInstance().setCurrentGameState(this);
		}

		public String getRawName() {
			return rawStateName;
		}
	}


}