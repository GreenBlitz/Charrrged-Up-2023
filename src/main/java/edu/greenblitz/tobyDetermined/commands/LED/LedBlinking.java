package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

public class LedBlinking extends LEDCommand{

    private Color color;

    public LedBlinking(Color wantedColor){
        color = wantedColor;
    }
    @Override
    public void execute() {
        led.setColor(color);
        Timer.delay(RobotMap.LED.BLINKING_ON_TIME);
        led.turnOff();
        Timer.delay(RobotMap.LED.BLINKING_OFF_TIME);
        led.setColor(color);
        Timer.delay(RobotMap.LED.BLINKING_ON_TIME);

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
