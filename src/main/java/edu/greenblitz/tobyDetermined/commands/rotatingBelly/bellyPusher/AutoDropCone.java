package edu.greenblitz.tobyDetermined.commands.rotatingBelly.bellyPusher;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoDropCone extends SequentialCommandGroup {
    public AutoDropCone(){
        addCommands(
                new ParallelCommandGroup(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.PRE_CONE_DROP), new InstantCommand(()-> Claw.getInstance().coneCatchMode())),
                new ParallelCommandGroup(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.POST_CONE_DROP), new PushCone()),
                new RetractPusher()
        );
    }
}
