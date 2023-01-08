package edu.greenblitz.pegasus.utils;

import edu.wpi.first.wpilibj2.command.WaitCommand;

public class WaitMilliSeconds extends WaitCommand {

	public WaitMilliSeconds(long time) {
		super(time / 1000.0);
	}

}
