package edu.greenblitz.pegasus.utils.commands.threading;

public interface IThreadable {

	void run();

	boolean isFinished();

	void atEnd();

	void atInit();

	default void atInterrupt() {
		atEnd();
	}

}
