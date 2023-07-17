package edu.greenblitz.tobyDetermined.commands.Auto.balance.bangBangBalance;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static edu.greenblitz.tobyDetermined.commands.Auto.balance.bangBangBalance.FullBalance.DEFAULT_SPEED;

public class PassAndBalance extends SequentialCommandGroup {

	public PassAndBalance(double speed) {
		super(new GetToRamp(speed * RobotMap.Swerve.Balance.GET_ON_SPEED_FACTOR),
				new GetOnRamp(speed * RobotMap.Swerve.Balance.GET_ON_SPEED_FACTOR),
				new GetToFloor(speed * RobotMap.Swerve.Balance.GET_TO_FLOOR_SPEED_FACTOR),
				new FullBalance(-speed)
		);
	}

	public PassAndBalance(boolean forward) {
		this(forward ? DEFAULT_SPEED : -DEFAULT_SPEED);
	}
}
