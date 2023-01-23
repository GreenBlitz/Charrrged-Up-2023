package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.OI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

public class ObjectInIntakeLED extends LEDCommand{

    public ObjectInIntakeLED(){

    }

    @Override
    public void execute() {
        led.setColor(Color.kGreen);
        Timer.delay(0.4);
        led.setColor(Color.kWhite);
        Timer.delay(0.2);
        led.setColor(Color.kGreen);
        Timer.delay(0.2);

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
