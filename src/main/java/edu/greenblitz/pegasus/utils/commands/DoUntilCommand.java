package edu.greenblitz.pegasus.utils.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

import java.util.function.BooleanSupplier;

public class DoUntilCommand extends ParallelDeadlineGroup {
	/**
	 * Creates a new ParallelDeadlineGroup. The given commands (including the deadline) will be
	 * executed simultaneously. The CommandGroup will finish when the deadline finishes, interrupting
	 * all other still-running commands. If the CommandGroup is interrupted, only the commands still
	 * running will be interrupted.
	 *
	 * @param commands the commands to be executed
	 */
	public DoUntilCommand(BooleanSupplier end, Command... commands) {
		super(new WaitUntilCommand(end), commands);
	}
}
