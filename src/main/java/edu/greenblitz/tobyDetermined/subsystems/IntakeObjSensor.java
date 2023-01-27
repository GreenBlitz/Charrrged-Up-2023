package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class IntakeObjSensor extends GBSubsystem {
	/**
	 * it senses the object inside (cone/cube) using the color sensor,
	 * and displays it on the dashboard
	 */

	private final Color kYellowTarget = new Color(0.34509f, 0.51764f, 0.1333f);
	private final Color kPurpleTarget = new Color(0.25f, 0.328f, 0.456f);
	public final ColorMatch colorMatcher = new ColorMatch();

	private static IntakeObjSensor instance;

	public RobotMap.HandleObj.Obj curObj = null;

	private IntakeObjSensor(){
		colorMatcher.addColorMatch(kYellowTarget);
		colorMatcher.addColorMatch(kPurpleTarget);
		
		colorMatcher.setConfidenceThreshold(0.91);
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
		Color detectedColor = RobotMap.HandleObj.cs.getColor();

		/**
		 * Run the color match algorithm on our detected color
		 */
		ColorMatchResult match = colorMatcher.matchColor(detectedColor);
		
		if (match == null){
			curObj = RobotMap.HandleObj.Obj.NONE;
			return;
		}
		if (match.color == kYellowTarget) {
			curObj = RobotMap.HandleObj.Obj.CONE;
		} else if (match.color == kPurpleTarget){
			curObj = RobotMap.HandleObj.Obj.CUBE;
		}

		/**
		 * Open Smart Dashboard or Shuffleboard to see the color detected by the
		 * sensor.
		 */
		SmartDashboard.putNumber("Red", detectedColor.red);
		SmartDashboard.putNumber("Green", detectedColor.green);
		SmartDashboard.putNumber("Blue", detectedColor.blue);
		SmartDashboard.putNumber("Confidence", match.confidence);
	}
}
