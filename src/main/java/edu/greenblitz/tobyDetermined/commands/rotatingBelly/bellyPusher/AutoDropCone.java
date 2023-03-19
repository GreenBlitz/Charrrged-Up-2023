package edu.greenblitz.tobyDetermined.commands.rotatingBelly.bellyPusher;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoDropCone extends ProxyCommand {
    public AutoDropCone(){
        super(new SequentialCommandGroup(
                new InstantCommand(()-> RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH = 0.11),
                new ParallelCommandGroup(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.PRE_CONE_DROP), new InstantCommand(()-> Claw.getInstance().coneCatchMode())),
                new ParallelCommandGroup(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.POST_CONE_DROP), new PushCone()),
                new RetractPusher()));
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH = 0.054;
    }
}
