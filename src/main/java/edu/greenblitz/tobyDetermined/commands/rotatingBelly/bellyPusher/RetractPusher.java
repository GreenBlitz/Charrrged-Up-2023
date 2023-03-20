package edu.greenblitz.tobyDetermined.commands.rotatingBelly.bellyPusher;

import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.BellyPusher;
import edu.greenblitz.utils.GBCommand;

public class RetractPusher extends PusherCommand {


    @Override
    public void initialize() {
        pusher.closePiston();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}


