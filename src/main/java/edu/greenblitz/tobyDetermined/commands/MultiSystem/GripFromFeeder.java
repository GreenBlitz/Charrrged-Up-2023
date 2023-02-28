package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripAir;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.StayAtCurrentAngle;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class GripFromFeeder extends ParallelCommandGroup {
    public GripFromFeeder(){
        addCommands(
                (new GoToPosition(RobotMap.TelescopicArm.PresetPositions.FEEDER).andThen(new StayAtCurrentAngle())).alongWith(new GripAir())
        );
    }
}
