package edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullBalance extends SequentialCommandGroup {
	
	public static final double DEFAULT_SPEED = 1.0;
	
	public FullBalance(double speed){
		super(new GetOnRamp(speed), new BangBangBalance(speed));
	}
	
	public FullBalance(boolean forward){
		this(forward ? 1 : -1);
	}
}
