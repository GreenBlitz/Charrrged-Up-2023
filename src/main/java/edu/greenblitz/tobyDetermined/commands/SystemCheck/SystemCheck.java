package edu.greenblitz.tobyDetermined.commands.SystemCheck;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.FullIntake;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.FullOpenIntake;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.GripFromBelly;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.armarm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class SystemCheck extends SequentialCommandGroup {
    public SystemCheck() {
        super(
                new InstantCommand(ObjectSelector::selectCone),
                new WaitCommand(3).deadlineWith(
                        new FullOpenIntake(),
                        new GoToPosition(RobotMap.TelescopicArm.PresetPositions.PRE_INTAKE_GRAB_POSITION)
                ),
                new FullIntake(),
                new GripFromBelly(),
                new GoToPosition(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH),
                
                new InstantCommand(ObjectSelector::selectCube),
                new WaitCommand(3).deadlineWith(
                        new FullOpenIntake(),
                        new GoToPosition(RobotMap.TelescopicArm.PresetPositions.PRE_INTAKE_GRAB_POSITION)
                ),
                new FullIntake(),
                new GripFromBelly(),
                new GoToPosition(RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH)
        );
    }
}
