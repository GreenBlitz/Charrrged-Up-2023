package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.Field;
import edu.greenblitz.tobyDetermined.RobotMap;

public class MoveByVisionSupplier extends CombineJoystickMovement {
	public MoveByVisionSupplier(boolean isSlow) {
		super(isSlow, new AngPIDSupplier(Field.Apriltags.redApriltagLocationId1.toPose2d().getTranslation()));
	}
}
