package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class LedBlinking extends SequentialCommandGroup {

    private Color color;

    public LedBlinking(Color firstColor, Color secondColor){
        super(
               new SetLEDColor(firstColor),new WaitCommand(RobotMap.LED.BLINKING_TIME),
                new SetLEDColor(secondColor),new WaitCommand(RobotMap.LED.BLINKING_TIME)
        );
    }

    public LedBlinking(Color color){
        this(color, Color.kBlack);
    }
}
