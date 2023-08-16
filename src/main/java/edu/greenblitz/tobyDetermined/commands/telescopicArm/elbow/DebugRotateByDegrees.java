package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.wpi.first.math.util.Units;

public class DebugRotateByDegrees extends RotateToAngleRadians {
	public DebugRotateByDegrees(double angleInDegrees) {
		super(ElbowSub.getInstance().getAngleRadians() + Units.degreesToRadians(angleInDegrees));
	}
}
