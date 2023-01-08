package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;

import java.awt.*;


public class Indexing extends GBSubsystem {
	/**
	 * This class is in-charge of checking if an enemy ball is trying to get to the funnel (and eventually to the shooter)
	 * by using color sensor
	 *
	 * @see ColorSensorV3 colorSensorV3
	 */

	private static Indexing instance;
	private ColorSensorV3 cs;
	public DriverStation.Alliance alliance;

	private Indexing() {
		cs = new ColorSensorV3(I2C.Port.kOnboard);
		alliance = DriverStation.getAlliance();
	}

	public static Indexing getInstance() {
		if (instance == null) {
			instance = new Indexing();
		}
		return instance;
	}


	/**
	 * @return the color that is in front of the sensor (as a DriverStation.Alliance enum)
	 */
	public DriverStation.Alliance getBallColor() {
		float[] color = Color.RGBtoHSB(cs.getRed(), cs.getGreen(), cs.getBlue(), new float[3]);
		if (color[0] >= 0.05 && color[0] <= 0.2) {
			return DriverStation.Alliance.Red;
		} else if (color[0] >= 0.4 && color[0] < 0.6) {
			return DriverStation.Alliance.Blue;
		} else {
			return DriverStation.Alliance.Invalid;
		}
	}

	/**
	 * @return true if enemy ball in front of the sensor
	 */
	public boolean isEnemyBallInSensor() {
		DriverStation.Alliance a = instance.getBallColor();
		return a != alliance
				&& a != DriverStation.Alliance.Invalid;
	}

	/**
	 * @return true is our team's ball is in front of the sensor.
	 */
	public boolean isTeamsBallInSensor() {
		DriverStation.Alliance a = instance.getBallColor();
		return a == alliance
				&& a != DriverStation.Alliance.Invalid;
	}


}