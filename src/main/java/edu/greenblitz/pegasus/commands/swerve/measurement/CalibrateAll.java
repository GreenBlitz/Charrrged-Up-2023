package edu.greenblitz.pegasus.commands.swerve.measurement;

import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CalibrateAll extends SequentialCommandGroup {
	public CalibrateAll(){
		super(
				new CalibrateLampreyByNeo(SwerveChassis.Module.FRONT_LEFT),
				new CalibrateLampreyByNeo(SwerveChassis.Module.FRONT_RIGHT),
				new CalibrateLampreyByNeo(SwerveChassis.Module.BACK_LEFT),
				new CalibrateLampreyByNeo(SwerveChassis.Module.BACK_RIGHT)
		);
	}
}
