package edu.greenblitz.utils;

import edu.wpi.first.wpilibj.Timer;

public class InterruptableWaitCommand extends GBCommand{

    private double time;
    private Timer timer;

    public InterruptableWaitCommand (double time){
        this.time = time;
        this.timer = new Timer();
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(this.time);
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
    }
}
