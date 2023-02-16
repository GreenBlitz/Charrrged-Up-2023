package edu.greenblitz.tobyDetermined.commands.MultiSubSystemCommands;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.RetractRoller;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeGameObjectSensor;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullGrip extends SequentialCommandGroup {

    public FullGrip(){
        addCommands(new ExtendRoller()
                .alongWith(
                        new RunRoller().until(() ->
                                (IntakeGameObjectSensor.getInstance().getCurObject() != IntakeGameObjectSensor.GameObject.NONE) ||
                                        RotatingBelly.getInstance().getGameObject() != IntakeGameObjectSensor.GameObject.NONE)
        ));
        addCommands(
                new GoToPosition(RobotMap.telescopicArm.presetPositions.INTAKE_DROP_POSITION)
                        .andThen(new RetractRoller())
        );
    }

}
