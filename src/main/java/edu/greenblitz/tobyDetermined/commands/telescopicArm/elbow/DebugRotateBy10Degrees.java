package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;

public class DebugRotateBy10Degrees extends RotateToAngleRadians {

	public DebugRotateBy10Degrees() {
		super(Elbow.getInstance().getAngleRadians() + Math.toRadians(10));
	}
}
