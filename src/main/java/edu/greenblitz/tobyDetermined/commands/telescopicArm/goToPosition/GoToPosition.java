package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.ConsoleLog;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class GoToPosition extends SequentialCommandGroup {

	public GoToPosition(double targetLengthInMeters, double targetAngleInRads) {
        /*
           if the desired position needs to pass through the entrance zone and the extension to long the movement is split to multiple parts:
           shrink to passing length,
           rotate arm
           extend to wanted length
         */
		addCommands(new SimpleGoToPosition(targetLengthInMeters, targetAngleInRads).alongWith(new ConsoleLog("GoToPos", "simple"))
				.unless(() -> !
						(Extender.getHypotheticalState(targetLengthInMeters) == Extender.ExtenderState.IN_WALL_LENGTH
								|| Elbow.getInstance().isInTheSameState(targetAngleInRads)
								|| (Elbow.getInstance().state == Elbow.ElbowState.WALL_ZONE)
								|| (Elbow.getHypotheticalState(targetAngleInRads) == Elbow.ElbowState.WALL_ZONE)
						)
				)
		);


		addCommands(new GoToWallZone().andThen(new PassWallAndExtend(targetLengthInMeters, targetAngleInRads)).alongWith(new ConsoleLog("GoToPos", "out in or in out"))
				.unless(() -> !
						((Elbow.getInstance().state == Elbow.ElbowState.OUT_ROBOT && Elbow.getHypotheticalState(targetAngleInRads) == Elbow.ElbowState.IN_BELLY)
								|| (Elbow.getInstance().state == Elbow.ElbowState.IN_BELLY && Elbow.getHypotheticalState(targetAngleInRads) == Elbow.ElbowState.OUT_ROBOT)
						)
				)
		);
	}

	public GoToPosition(RobotMap.TelescopicArm.PresetPositions position) {
		this(position.distance, position.angleInRadians);
	}


}
