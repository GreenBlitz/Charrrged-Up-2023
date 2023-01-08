package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.compressor.HandleCompressor;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Pneumatics extends GBSubsystem {
	private Compressor compressor;
	private static Pneumatics instance;

	private Pneumatics() { //todo check if created automaticlly by solenoid
		compressor = new Compressor(RobotMap.Pegasus.Pneumatics.PCM.PCM_ID, PneumaticsModuleType.CTREPCM);
	}

	public static void init() {
		if(instance == null){
			instance = new Pneumatics();
			instance.setDefaultCommand(new HandleCompressor());
		}
	}

	public static Pneumatics getInstance() {
		return instance;
	}

	public double getPressure() {
		return this.compressor.getPressure();
	}

	public void setCompressor(boolean compress) {
		if(compress) {
			compressor.enableDigital();
		} else {
			compressor.disable();
		}
	}

	public boolean isEnabled() {
		return compressor.enabled();
	}

	public void reset(){
		setCompressor(false);
	}

	@Override
	public void periodic() {
		super.periodic();
	}
}
