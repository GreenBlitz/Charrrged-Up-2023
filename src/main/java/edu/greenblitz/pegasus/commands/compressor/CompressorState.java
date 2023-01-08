package edu.greenblitz.pegasus.commands.compressor;

public class CompressorState extends CompressorCommand{
	@Override
	public void execute() {
		System.out.println(compressor.isEnabled());
	}
}
