package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullGripCone extends SequentialCommandGroup {

	public FullGripCone(){

		super(
				new InstantCommand(() -> SmartDashboard.putBoolean("ssssss", false)),
				new DropCone(),
				new DisableClawCubeRotation().raceWith(
						new GoToPosition(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_CONE_POSITION)),
				new InstantCommand(() -> SmartDashboard.putBoolean("ssssss", true)),
				new GripCone().raceWith(new WaitCommand(0.3)),
				new RotateClaw().raceWith(new WaitCommand(0.2)),
				new DisableClawCubeRotation().raceWith(new WaitCommand(0.3))


		);

	}
}
