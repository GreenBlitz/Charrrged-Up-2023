package edu.greenblitz.pegasus.commands.compressor;

import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.greenblitz.pegasus.subsystems.Pneumatics;

public abstract class CompressorCommand extends GBCommand {

	protected Pneumatics compressor;

	public CompressorCommand() {
		compressor = Pneumatics.getInstance();
		require(compressor);
	}

}