package edu.greenblitz.tobyDetermined.commands.SystemCheck;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.CloseIntakeAndAlign;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.FullOpenIntake;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.GripFromBelly;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetDown;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetUp;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ReleaseObject;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToGrid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.ZigHail;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class SystemCheck extends SequentialCommandGroup {
    public SystemCheck() {
        super(
                new InstantCommand(ObjectSelector::flipSelection),
                new GoToPosition(RobotMap.TelescopicArm.PresetPositions.PRE_INTAKE_GRAB_POSITION),
                new FullOpenIntake().raceWith(new WaitCommand(2)),
                new CloseIntakeAndAlign(),
                new RotateOutDoorDirection().raceWith(new WaitCommand(1)),
                new GripFromBelly().raceWith(new WaitCommand(5)),
                new ZigHail(),
                new MoveSelectedTargetUp(),
                new MoveSelectedTargetUp(),
                new GoToGrid(),
                new ReleaseObject(),

                new WaitCommand(2),

                new GoToPosition(RobotMap.TelescopicArm.PresetPositions.PRE_INTAKE_GRAB_POSITION),
                new InstantCommand(ObjectSelector::flipSelection),
                new FullOpenIntake().raceWith(new WaitCommand(2)),
                new CloseIntakeAndAlign(),
                new RotateOutDoorDirection().raceWith(new WaitCommand(1)),
                new GripFromBelly().raceWith(new WaitCommand(5)),
                new MoveSelectedTargetDown(),
                new ZigHail(),
                new GoToGrid(),
                new ReleaseObject()


        );
    }
}
