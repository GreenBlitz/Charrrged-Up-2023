package edu.greenblitz.pegasus.utils.commands.threading;

import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ThreadedCommand extends GBCommand {

	private Thread myThread;
	private IThreadable threadable;
	private Runnable wrapper;
	private boolean shouldStop;

	protected ThreadedCommand(Subsystem... req) {
		this(null, req);
	}

	public ThreadedCommand(IThreadable func, Subsystem... req) {
		threadable = func;

		for (Subsystem sys : req) {
			require(sys);
		}
	}

	public void setThreadable(IThreadable newThreadable) {
		threadable = newThreadable;
	}

	@Override
	public void end(boolean interrupted) {
		shouldStop = true;
		if (interrupted)
			threadable.atInterrupt();
		else
			threadable.atEnd();
	}

	@Override
	public void initialize() {

		wrapper = () -> {
			while (!shouldStop && !isFinished()) {
				threadable.run();
			}
		};

		myThread = new Thread(wrapper);

		shouldStop = false;
		threadable.atInit();
		myThread.start();
	}

	public void stop() {
		shouldStop = true;
	}

	@Override
	public boolean isFinished() {
		return threadable.isFinished() || shouldStop;
	}
}
