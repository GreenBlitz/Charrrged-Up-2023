package edu.greenblitz.pegasus.utils.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TimedCommand extends ParallelDeadlineGroup {
	/**
	 * Creates a new ParallelDeadlineGroup. The given commands will be
	 * executed simultaneously. The CommandGroup will finish when the time ends, interrupting
	 * all other still-running commands. If the CommandGroup is interrupted, only the commands still
	 * running will be interrupted.
	 *
	 * @param time     the length of time the commands will be activated for
	 * @param commands the commands to be executed
	 */
	public TimedCommand(double time, Command... commands) {
		super(new WaitCommand(time), commands);
	}
}
