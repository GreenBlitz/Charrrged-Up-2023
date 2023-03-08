package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.BellyPusher;
import edu.greenblitz.utils.GBCommand;

public class PushCone extends GBCommand {
    BellyPusher pusher = BellyPusher.getInstance();
    public PushCone(){
        require(pusher);
    }

    @Override
    public void initialize() {
        pusher.closePiston();
    }
}
