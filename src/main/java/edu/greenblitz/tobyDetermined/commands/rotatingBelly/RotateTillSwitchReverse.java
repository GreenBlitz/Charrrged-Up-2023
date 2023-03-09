package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.RotatingBelly;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class RotateTillSwitchReverse extends ParallelDeadlineGroup {
	public RotateTillSwitchReverse() {
		super(
				new WaitUntilCommand(() -> RotatingBelly.getInstance().isLimitSwitchPressed()).andThen(new WaitCommand(RobotMap.RotatingBelly.ROTATE_FROM_STOP_TO_SWITCH_TIME)),
				new RotateByPower(RobotMap.RotatingBelly.ROTATING_POWER)
		);
	}
}
