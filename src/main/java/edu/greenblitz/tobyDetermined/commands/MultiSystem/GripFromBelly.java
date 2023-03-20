package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.ConsoleLog;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DropCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripAir;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripBelly;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

public class GripFromBelly extends ParallelDeadlineGroup {
    public GripFromBelly(){
        super(
                new ConditionalCommand(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_CONE_POSITION),
                        new GoToPosition(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_CUBE_POSITION),
                        ObjectSelector::IsCone),
                new ConditionalCommand(new GripCube(), new DropCone(), ObjectSelector::IsCube)
                        .andThen(new GripBelly())

        );


    }
}
