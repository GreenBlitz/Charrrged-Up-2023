package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.OI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

public class ObjectInIntakeLED extends LEDCommand{

    public ObjectInIntakeLED(){

    }

    @Override
    public void initialize() {
        led.setColor(Color.kGreen);
        Timer.delay(0.4);
        led.setColor(Color.kWhite);
        Timer.delay(0.2);
        led.setColor(Color.kGreen);
        OI.getInstance().getMainJoystick().rumble(true,0.5);
        OI.getInstance().getSecondJoystick().rumble(true,0.5);
        Timer.delay(0.4);
        OI.getInstance().getMainJoystick().rumble(true,0);
        OI.getInstance().getSecondJoystick().rumble(true,0);
    }
}
