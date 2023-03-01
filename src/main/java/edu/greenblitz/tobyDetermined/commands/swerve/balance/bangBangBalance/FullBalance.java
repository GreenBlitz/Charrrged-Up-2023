package edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullBalance extends SequentialCommandGroup {
	
	public static final double DEFAULT_SPEED = 1;
	
	public FullBalance(double speed){
		super(new GetToRamp(speed * 0.7), new GetOnRamp(speed * 0.7  ), new BangBangBalance(speed * 0.4));
	}
	
	public FullBalance(boolean forward){
		this(forward ? DEFAULT_SPEED : -DEFAULT_SPEED);
	}
}
