package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.subsystems.Limelight;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;

import java.util.function.DoubleSupplier;

public class VisionTargetSupplier implements DoubleSupplier {
	double target;
	double timeStamp;
	boolean hasTarget = false;

	/**
	 * checks if the cam has target and return the target of the cam
	 * if the cam doesn't have target it return the current angle of the robot
	 * if we had one target he returns the last seen target
	 * @return
	 */
	@Override
	public double getAsDouble() {
		hasTarget = hasTarget || Limelight.getInstance().FindTarget();
		if (!hasTarget){
			return SwerveChassis.getInstance().getChassisAngle();
		}
		if (!Limelight.getInstance().FindTarget() || timeStamp == Limelight.getInstance().getTimeStamp()){return target;}
		target = Limelight.getInstance().fieldRelativeTargetYaw();
		timeStamp = Limelight.getInstance().getTimeStamp();
		return target;
	}
}
