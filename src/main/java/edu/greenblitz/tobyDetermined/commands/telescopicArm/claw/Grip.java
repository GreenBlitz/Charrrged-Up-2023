package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

public class Grip extends ConditionalCommand {

	public Grip(){
		super(new GripCube(),new GripCone(),ObjectSelector::IsCube);
	}

}
