package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.BellyPusher;
import edu.greenblitz.utils.GBCommand;

public class RetractPusher extends GBCommand {


    BellyPusher pusher = BellyPusher.getInstance();

    public RetractPusher (){
        require(pusher);
    }

    @Override
    public void initialize() {
        pusher.openPiston();
    }
}
