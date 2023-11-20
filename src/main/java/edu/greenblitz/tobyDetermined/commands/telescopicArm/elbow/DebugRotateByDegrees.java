package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.wpi.first.math.util.Units;

public class DebugRotateByDegrees extends RotateToAngleRadians {
	public DebugRotateByDegrees(double angleInDegrees) {
		super(Elbow.getInstance().getAngleRadians() + Units.degreesToRadians(angleInDegrees));
	}
}
