package edu.greenblitz.tobyDetermined.commands.LED;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

public class DefaultColor extends LEDCommand{



    public DefaultColor(){
    }

    @Override
    public void execute() {
        Timer.delay(0.2);
        led.setColor(led.getDefaultColor());
    }
}
