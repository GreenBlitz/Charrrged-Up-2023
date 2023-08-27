package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class GoToPosition extends SequentialCommandGroup {

	public GoToPosition(double targetLengthInMeters, double targetAngleInRads) {
        /*
           if the desired position needs to pass through the entrance zone and the extension to long the movement is split to multiple parts:
           shrink to passing length,
           rotate arm
           extend to wanted length
         */
		addCommands(new ConditionalCommand(
				new SimpleGoToPosition(targetLengthInMeters, targetAngleInRads),
				new PassWallAndExtend(targetLengthInMeters, targetAngleInRads),
				() ->
				(((ElbowSub.getInstance().isInTheSameState(targetAngleInRads) && ElbowSub.getInstance().state != ElbowSub.ElbowState.WALL_ZONE) || (ElbowSub.getInstance().getState() == ElbowSub.ElbowState.WALL_ZONE && Extender.getInstance().getState().shorterOrEqualTo(Extender.ExtenderState.IN_WALL_LENGTH))
		))));


	}

	public GoToPosition(RobotMap.TelescopicArm.PresetPositions position) {
		this(position.distance, position.angleInRadians);
	}


}
