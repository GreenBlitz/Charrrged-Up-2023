package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class IntakeObjSensor extends GBSubsystem {
	/**
	 * it senses the object inside (cone/cube) using the color sensor,
	 * and displays it on the dashboard
	 */
	public static final ColorSensorV3 cs = new ColorSensorV3(I2C.Port.kOnboard);
	public enum Obj {
		CONE,
		CUBE,
		NONE,
	}

	private final Color kYellowTarget = new Color(0.477f, 0.478f, 0.063f);
	private final Color kPurpleTarget = new Color(0.27f, 0.355f, 0.357f);
	public final ColorMatch colorMatcher = new ColorMatch();

	private static IntakeObjSensor instance;

	public Obj curObj = null;

	private IntakeObjSensor(){
		colorMatcher.addColorMatch(kYellowTarget);
		colorMatcher.addColorMatch(kPurpleTarget);
		
		colorMatcher.setConfidenceThreshold(0.9);
	}

	public static IntakeObjSensor getInstance() {
		if(instance == null) instance = new IntakeObjSensor();
		return instance;
	}

	@Override
	public void periodic() {
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
		Color detectedColor = cs.getColor();

		/**
		 * Run the color match algorithm on our detected color
		 */
		ColorMatchResult match = colorMatcher.matchColor(detectedColor);

		SmartDashboard.putNumber("Red", detectedColor.red);
		SmartDashboard.putNumber("Green", detectedColor.green);
		SmartDashboard.putNumber("Blue", detectedColor.blue);
		SmartDashboard.putNumber("Proxy", cs.getProximity());

		if (match == null){
			curObj = Obj.NONE;
			return;
		}
		if (match.color == kYellowTarget) {
			curObj = Obj.CONE;
		} else if (match.color == kPurpleTarget){
			curObj = Obj.CUBE;
		}

		SmartDashboard.putNumber("Confidence", match.confidence);
	}
}
