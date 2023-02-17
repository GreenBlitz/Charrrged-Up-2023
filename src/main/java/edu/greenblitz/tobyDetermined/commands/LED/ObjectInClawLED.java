package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;


public class ObjectInClawLED extends LEDCommand{

    @Override
    public void execute() {
        led.setBackgroundColor(Color.kBlue);

        new LedBlinking(Color.kBlue).schedule();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
