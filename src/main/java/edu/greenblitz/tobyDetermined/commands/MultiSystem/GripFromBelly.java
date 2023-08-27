package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateInDoorDirection;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DropCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripBelly;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.*;

public class GripFromBelly extends SequentialCommandGroup {
    public GripFromBelly(){
        super(
                new ConditionalCommand(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_CONE_POSITION),
                        new GoToPosition(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_CUBE_POSITION),
                        ObjectSelector::IsCone)
                .deadlineWith(new ConditionalCommand(new GripCube().alongWith(new RotateInDoorDirection()), new DropCone(), ObjectSelector::IsCube)),
                        new GripBelly()

        );


    }
}
