package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveToGrid;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ResetVisionMoveToPose extends SequentialCommandGroup {

	public ResetVisionMoveToPose(){
		super(new InstantCommand(()-> SwerveChassis.getInstance().resetToVision()),
				new WaitCommand(0.04),
				new MoveToGrid()
				);
	}


}
