package edu.greenblitz.tobyDetermined.commands.rotatingBelly.bellyPusher;

import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.BellyPusher;
import edu.greenblitz.utils.GBCommand;

public abstract class PusherCommand extends GBCommand {
	BellyPusher pusher;

	public PusherCommand(){
		pusher = BellyPusher.getInstance();
		require(pusher);
	}
}
