package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;

public class MoveByVisionSupplier extends CombineJoystickMovement {
	public MoveByVisionSupplier(boolean isSlow) {
		super(isSlow, new AngPIDSupplier(RobotMap.Vision.apriltagLocation.toPose2d().getTranslation()));
	}
}
