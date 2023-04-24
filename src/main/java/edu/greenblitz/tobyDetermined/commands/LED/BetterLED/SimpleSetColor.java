package edu.greenblitz.tobyDetermined.commands.LED.BetterLED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.LED.LEDCommand;
import edu.wpi.first.wpilibj.util.Color;

public class SimpleSetColor extends LEDCommand {

    private Color color;

    private int startingIndex;
    private int endingIndex;

    public SimpleSetColor (int startingIndex,int endingIndex,Color color){
        led.setColor(startingIndex,endingIndex,color);
    }
    public SimpleSetColor (Color color){
        led.setColor(startingIndex,endingIndex,color);
    }
    public SimpleSetColor (RobotMap.LED.Sections section, Color color){
        led.setColor(section.start,section.end,color);
    }




}
