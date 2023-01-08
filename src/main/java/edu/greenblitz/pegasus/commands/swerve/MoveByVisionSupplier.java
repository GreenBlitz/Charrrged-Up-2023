package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.Limelight;
import edu.wpi.first.math.geometry.Translation2d;

import java.util.function.DoubleSupplier;

public class MoveByVisionSupplier extends CombineJoystickMovement {
	public MoveByVisionSupplier(boolean isSlow) {
		super(isSlow, new AngPIDSupplier(RobotMap.Pegasus.Vision.apriltagLocation.toPose2d().getTranslation()));
	}
}
