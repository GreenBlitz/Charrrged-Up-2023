package edu.greenblitz.tobyDetermined.commands.telescopicArm;


import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj2.command.InstantCommand;


public class RewritePresetPosition extends InstantCommand {

	@Override
	public void initialize() {
		double angleInRads = ElbowSub.getInstance().getAngleRadians();
		Grid.getInstance().rewriteArmPositionInSelectedPose(angleInRads, Extender.getInstance().getLength());
	}
}
