package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.util.Color;

public class BackgroundColor extends LEDCommand {

    @Override
    public void execute() {
        led.setColor(led.getBackgroundColor());
    }
}
