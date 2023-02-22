package edu.greenblitz.tobyDetermined.commands.telescopicArm;


import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj2.command.InstantCommand;


public class RewritePresetPosition extends InstantCommand {

	@Override
	public void initialize() {
		double angleInDeg = Math.toDegrees(Elbow.getInstance().getAngleRadians());
		Grid.getInstance().rewriteArmPositionInSelectedPose(angleInDeg, Extender.getInstance().getLength());
	}
}
