package edu.greenblitz.tobyDetermined.commands.MultiSubSystemCommands;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.FullRotateToPlace;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.GoToPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeGameObjectSensor;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class GetObjectToClaw extends SequentialCommandGroup {

    public GetObjectToClaw() {

        addCommands(
                new GoToPosition(
                        RobotMap.telescopicArm.presetPositions.INTAKE_GRAB_POSITION)
                        .alongWith(
                                new ConditionalCommand(new FullRotateToPlace(),new InstantCommand(()-> RotatingBelly.getInstance().stop()),
                                        ()-> RotatingBelly.getInstance().getGameObject() == IntakeGameObjectSensor.GameObject.CONE)));

        addCommands(new ConditionalCommand(new GripCone(),new GripCube(),() -> RotatingBelly.getInstance().getGameObject() == IntakeGameObjectSensor.GameObject.CONE));

    }

}
