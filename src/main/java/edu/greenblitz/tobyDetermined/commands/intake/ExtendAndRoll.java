package edu.greenblitz.tobyDetermined.commands.intake;

import edu.greenblitz.tobyDetermined.Nodesssss.CurrentNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.NodeFullPathCommand;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ExtendAndRoll extends SequentialCommandGroup {

	public ExtendAndRoll(boolean needCheck){
		if (needCheck) {
			if (!NodeBase.getNode(CurrentNode.getInstance().getCurrentNode()).isGriperPos()) {
				new SequentialCommandGroup(
						new NodeFullPathCommand(RobotMap.TelescopicArm.PresetPositions.REST_ABOVE_BELLY),
						new ExtendRoller(),
						new RunRoller()
				).schedule();
			}
		}
		else {
			new SequentialCommandGroup(
					new ExtendRoller(),
					new RunRoller()
			).schedule();
		}
	}
}
