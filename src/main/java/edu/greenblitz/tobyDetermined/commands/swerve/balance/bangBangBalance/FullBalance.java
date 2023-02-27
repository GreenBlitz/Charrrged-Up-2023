package edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullBalance extends SequentialCommandGroup {
	
	public FullBalance(double speed){
		super(new GetOnRamp(speed), new BangBangBalance(speed));
	}
}
