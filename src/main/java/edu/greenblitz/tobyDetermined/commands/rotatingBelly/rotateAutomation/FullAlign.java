package edu.greenblitz.tobyDetermined.commands.rotatingBelly.rotateAutomation;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateInDoorDirection;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.RotatingBelly;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.*;

public class FullAlign extends SequentialCommandGroup {


    public static final double FINAL_TIME = 3;
    public static final double EXTRA_TIME = 0.2; //seconds

    public FullAlign(){
        addCommands(
                new SequentialCommandGroup(
                        new RotateInDoorDirection().until(()-> RotatingBelly.getInstance().isLimitSwitchPressed()),
                        new RotateInDoorDirection().until(()-> !RotatingBelly.getInstance().isLimitSwitchPressed()),
//                        new RotateInDoorDirection().raceWith(new WaitCommand(EXTRA_TIME)),

                        new RotateOutDoorDirection().until(()-> RotatingBelly.getInstance().isLimitSwitchPressed()),
                        new RotateOutDoorDirection().raceWith(new WaitCommand(EXTRA_TIME)),

                        new InstantCommand(()-> LED.getInstance().setColor(Color.kOrchid))
                ).raceWith(new WaitCommand(FINAL_TIME)));
    }


}
