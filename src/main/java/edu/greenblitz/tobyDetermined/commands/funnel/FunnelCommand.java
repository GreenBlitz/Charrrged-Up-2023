package edu.greenblitz.tobyDetermined.commands.funnel;

import edu.greenblitz.tobyDetermined.subsystems.Funnel;
import edu.greenblitz.utils.GBCommand;

public abstract class FunnelCommand extends GBCommand {

	protected Funnel funnel;

	public FunnelCommand() {
		funnel = Funnel.getInstance();
		require(funnel);
	}
}
