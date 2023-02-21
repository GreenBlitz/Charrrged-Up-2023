package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class BellyGameObjectSensor extends GBSubsystem {
	/**
	 * it senses the object inside (cone/cube) using the color sensor,
	 * and displays it on the dashboard
	 */
	public final ColorSensorV3 cs;
	public enum GameObject {
		CONE,
		CUBE,
		NONE,
	}
	private static Color YELLOW_TARGET;
	private static Color PURPLE_TARGET;
	public final ColorMatch colorMatcher;

	private static BellyGameObjectSensor instance;

	private GameObject curObject = null;
	private static final double confidenceThreshold  = 0.9;;
	private static boolean shouldLogCalibration = false;
	private BellyGameObjectSensor(){
		cs = new ColorSensorV3(I2C.Port.kOnboard);


		YELLOW_TARGET = new Color(0.477f, 0.478f, 0.063f);
		PURPLE_TARGET = new Color(0.27f, 0.355f, 0.357f);

		colorMatcher = new ColorMatch();
		colorMatcher.addColorMatch(YELLOW_TARGET);
		colorMatcher.addColorMatch(PURPLE_TARGET);

		colorMatcher.setConfidenceThreshold(confidenceThreshold);


	}

	protected static BellyGameObjectSensor getInstance() {
		init();
		return instance;
	}

	public static void init(){
		if(instance == null) {
			instance = new BellyGameObjectSensor();
		}
	}

	public void CalibrteDashBoard(Color detectedColor, ColorMatchResult match){
		if(match != null) SmartDashboard.putNumber("Confidence", match.confidence);
		SmartDashboard.putNumber("Red", detectedColor.red);
		SmartDashboard.putNumber("Green", detectedColor.green);
		SmartDashboard.putNumber("Blue", detectedColor.blue);
		SmartDashboard.putNumber("Proxy", cs.getProximity());
	}

	public GameObject getCurObject (){
		return curObject;
	}

	public boolean isObjectIn(){
		return getCurObject() == GameObject.NONE;
	}

	@Override
	public void periodic() {
		/**
		 * The method GetColor() returns a normalized color value from the sensor and can be
		 * useful if outputting the color to an RGB LED or similar. To
		 * read the raw color, use GetRawColor().
		 *
		 * The color sensor works best when within a few inches from an object in
		 * well lit conditions (the built-in LED is a big help here!). The farther
		 * an object is the more light from the surroundings will bleed into the
		 * measurements and make it difficult to accurately determine its color.
		 */
		Color detectedColor = cs.getColor();

		ColorMatchResult match = colorMatcher.matchColor(detectedColor);

		/**
		 * Run the color match algorithm on our detected color
		 */
		if (match == null){
			curObject = GameObject.NONE;
		} else if (match.color == YELLOW_TARGET) {
			curObject = GameObject.CONE;
		} else if (match.color == PURPLE_TARGET){
			curObject = GameObject.CUBE;
		}

		if(shouldLogCalibration) this.CalibrteDashBoard(detectedColor, match);
	}
}
