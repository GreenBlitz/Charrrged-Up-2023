package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.NeoExtender;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.ReplayExtender;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.SimulationExtender;

public class ClawFactory {

	public static IClaw create(){
		switch (RobotMap.ROBOT_TYPE) {
			case FRANKENSTEIN:
				return new RealClaw();
			case REPLAY:
				return new ReplayClaw();
			case PEGA_SWERVE:
			case SIMULATION:
			default:
				return new SimulationClaw();
		}
	}
}
