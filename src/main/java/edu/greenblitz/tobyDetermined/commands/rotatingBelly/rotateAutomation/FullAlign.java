package edu.greenblitz.tobyDetermined.commands.rotatingBelly.rotateAutomation;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateInDoorDirection;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.RotatingBelly;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.*;

public class FullAlign extends SequentialCommandGroup {

    public static final double EXTRA_TIME = 1; //seconds
    public static final double POWER = 0.7; //seconds

    public FullAlign(){
        addCommands(
                new ParallelRaceGroup(
                        new RotateOutDoorDirection(),
                        new WaitUntilCommand(()-> RotatingBelly.getInstance().isLimitSwitchPressed()).andThen(new WaitCommand(RobotMap.RotatingBelly.ROTATE_FROM_SWITCH_TO_STOP_TIME))
                ),
                new RotateInDoorDirection().raceWith(
                        new WaitUntilCommand(()-> RotatingBelly.getInstance().isLimitSwitchPressed()).andThen(new WaitCommand(RobotMap.RotatingBelly.ROTATE_FROM_STOP_TO_SWITCH_TIME))
                ),
                new InstantCommand(()-> LED.getInstance().setColor(Color.kOrchid))
    );
    }
}
