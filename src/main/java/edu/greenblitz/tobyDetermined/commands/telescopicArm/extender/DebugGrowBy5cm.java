package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class DebugGrowBy5cm extends ExtendToLength {

	public DebugGrowBy5cm() {
		super(Extender.getInstance().getLength()+0.05);
	}
}
