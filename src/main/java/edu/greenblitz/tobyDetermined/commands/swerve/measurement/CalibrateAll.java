package edu.greenblitz.tobyDetermined.commands.swerve.measurement;

import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CalibrateAll extends SequentialCommandGroup {
	public CalibrateAll() {
		super(
				new CalibrateLampreyByNeo(SwerveChassis.Module.FRONT_LEFT),
				new CalibrateLampreyByNeo(SwerveChassis.Module.FRONT_RIGHT),
				new CalibrateLampreyByNeo(SwerveChassis.Module.BACK_LEFT),
				new CalibrateLampreyByNeo(SwerveChassis.Module.BACK_RIGHT)
		);
	}
}
