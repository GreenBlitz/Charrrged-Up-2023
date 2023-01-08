package edu.greenblitz.pegasus.commands.compressor;

public class CompressorOff extends CompressorCommand {

	@Override
	public void initialize() {
		compressor.setCompressor(false);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

}
