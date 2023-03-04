package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.ConsoleLog;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripAir;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripBelly;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class GripFromBelly extends ParallelCommandGroup {
    public GripFromBelly(){
        addCommands(
                new GripCube().raceWith(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_POSITION)).andThen(new GripBelly())
        );
    }
}
