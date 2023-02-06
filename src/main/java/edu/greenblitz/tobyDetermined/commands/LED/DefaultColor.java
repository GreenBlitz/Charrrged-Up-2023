package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.util.Color;

public class DefaultColor extends LEDCommand {


    public int colorChangeCounter;

    @Override
    public void initialize() {
        colorChangeCounter = 0;
    }

    @Override
    public void execute() {

        led.setColor(new Color (colorChangeCounter,100,100));
        colorChangeCounter++;
        if(colorChangeCounter > RobotMap.LED.LENGTH){
            colorChangeCounter = 0;
        }

    }
}
