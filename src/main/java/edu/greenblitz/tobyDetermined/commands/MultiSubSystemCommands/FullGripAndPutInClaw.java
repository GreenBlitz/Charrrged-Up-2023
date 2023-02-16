package edu.greenblitz.tobyDetermined.commands.MultiSubSystemCommands;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.FullRotateToPlace;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutOfDoor;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripFromClaw;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeGameObjectSensor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullGripAndPutInClaw extends SequentialCommandGroup {

    public FullGripAndPutInClaw(){
        RotatingBelly.getInstance().getGameObject();

        if(IntakeGameObjectSensor.getInstance().getCurObject() != IntakeGameObjectSensor.GameObject.NONE){
            addCommands(
                    new FullGrip()
                            .alongWith(new RotateOutOfDoor())
                            .andThen(new RotateOutOfDoor().raceWith(
                                    new WaitCommand(RobotMap.RotatingBelly.ROTATE_OUT_OF_DOOR_TIME)
                            ))
            );
        }

    }
}
