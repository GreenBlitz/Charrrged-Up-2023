package edu.greenblitz.tobyDetermined.commands.LED;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;


public class ObjectInClawLED extends LEDCommand{

    public ObjectInClawLED(){

    }

    @Override
    public void execute() {
        led.setColor(Color.kBlue);
        Timer.delay(0.4);
        led.setColor(Color.kWhite);
        Timer.delay(0.2);
        led.setColor(Color.kBlue);
        Timer.delay(0.2);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
