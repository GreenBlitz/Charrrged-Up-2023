package edu.greenblitz.pegasus.commands.compressor;

public class CompressorOn extends CompressorCommand {

	@Override
	public void initialize() {
		compressor.setCompressor(true);
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}

