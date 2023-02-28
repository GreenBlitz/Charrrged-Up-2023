package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.Grip;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class GripFromBelly extends ParallelCommandGroup {
    public GripFromBelly(){
        addCommands(
                new GoToPosition(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_POSITION).alongWith(new Grip())
        );
    }
}
