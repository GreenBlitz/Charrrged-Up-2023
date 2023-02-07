package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;


public class ObjectInClawLED extends LEDCommand{

    @Override
    public void initialize() {
        led.setBackgroundColor(Color.kBlue);

        led.setColor(Color.kBlue);
        Timer.delay(RobotMap.LED.BLINKING_ON_TIME);
        led.turnOff();
        Timer.delay(RobotMap.LED.BLINKING_OFF_TIME);
        led.setColor(Color.kBlue);
        Timer.delay(RobotMap.LED.BLINKING_ON_TIME);
        led.setBackgroundColor(Color.kBlue);
    }


}
