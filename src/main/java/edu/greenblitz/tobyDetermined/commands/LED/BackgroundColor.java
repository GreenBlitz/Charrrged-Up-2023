package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class BackgroundColor extends LEDCommand {

    @Override
    public void initialize() {
        led.setColor(led.getBackgroundColor());
    }

    @Override
    public void end(boolean interrupted) {

    }
}
