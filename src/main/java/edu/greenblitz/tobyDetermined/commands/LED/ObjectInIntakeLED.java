package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

public class ObjectInIntakeLED extends LEDCommand{


    @Override
    public void execute() {
        led.setBackgroundColor(Color.kRed);

        new LedBlinking(Color.kRed).schedule();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
