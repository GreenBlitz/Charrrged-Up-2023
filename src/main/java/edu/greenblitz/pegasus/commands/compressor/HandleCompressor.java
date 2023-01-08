package edu.greenblitz.pegasus.commands.compressor;

public class HandleCompressor extends CompressorCommand {

	private static final int TURN_ON_THRESHOLD = 30, TURN_OFF_THRESHOLD = 40;

	@Override
	public void execute() {
		if (compressor.getPressure() < TURN_ON_THRESHOLD) {
			compressor.setCompressor(true);
		}
		if (compressor.getPressure() > TURN_OFF_THRESHOLD) {
			compressor.setCompressor(false);
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}