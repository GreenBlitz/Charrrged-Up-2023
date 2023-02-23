package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.RobotMap;

public class DefaultColor extends LEDCommand{

    @Override
    public void initialize() {
        led.setBackgroundColor(RobotMap.LED.DEFAULT_COLOR);
    }

    @Override
    public void execute() {
        led.setColor(led.getBackgroundColor());
    }
}
