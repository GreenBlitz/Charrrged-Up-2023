package edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullBalance extends SequentialCommandGroup {
	
	public static final double DEFAULT_SPEED = 0.7;
	
	public FullBalance(double speed){
		super(new GetOnRamp(speed), new BangBangBalance(speed));
	}
	
	public FullBalance(boolean forward){
		this(forward ? DEFAULT_SPEED : -DEFAULT_SPEED);
	}
}
