package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

public class ObjectInIntakeLED extends LEDCommand{


    @Override
    public void initialize() {
        led.setBackgroundColor(Color.kRed);

        led.setColor(Color.kRed);
        Timer.delay(RobotMap.LED.BLINKING_ON_TIME);
        led.turnOff();
        Timer.delay(RobotMap.LED.BLINKING_OFF_TIME);
        led.setColor(Color.kRed);
        Timer.delay(RobotMap.LED.BLINKING_ON_TIME);

    }
}
