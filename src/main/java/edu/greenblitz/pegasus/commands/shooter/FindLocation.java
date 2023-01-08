package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.pegasus.subsystems.Limelight;
import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class FindLocation extends GBCommand {
	public FindLocation(){
		super();
		require((Subsystem) Limelight.getInstance());
	}

	@Override
	public void initialize() {

		System.out.println(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv"));
		System.out.println((Limelight.getInstance().getYawTarget()));

		//System.out.println((Limelight.getInstance().getLocation()));


	}

	@Override
	public boolean isFinished() {
		return true;
	}
}