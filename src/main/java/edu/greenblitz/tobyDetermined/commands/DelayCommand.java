package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.Timer;

public class DelayCommand extends GBCommand {
    private double timeInSeconds;
    public DelayCommand (double timeInSeconds){
        this.timeInSeconds = timeInSeconds;
    }

    @Override
    public void initialize() {
        Timer.delay(timeInSeconds);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
