package edu.greenblitz.tobyDetermined.subsystems;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

public abstract class GBSubsystem implements Subsystem {
	
	
	public GBSubsystem() {
		CommandScheduler.getInstance().registerSubsystem(this);
	}
	
}
