package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateInDoorDirection;
<<<<<<< HEAD
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DropCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripBelly;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
=======
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.*;
>>>>>>> origin/master
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.*;

public class GripFromBelly extends SequentialCommandGroup {
    public GripFromBelly(){
        super(
                new ConditionalCommand(
                        new FullGripCone(),
                        new FullGripCube(),
                        ObjectSelector::IsCone)


        );


    }
}
