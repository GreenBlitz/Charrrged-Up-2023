package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.armarm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

public class GripAir extends ConditionalCommand {

	public GripAir(){
		super(new GripCube(),new GripCone(),ObjectSelector::IsCube);
	}

}
