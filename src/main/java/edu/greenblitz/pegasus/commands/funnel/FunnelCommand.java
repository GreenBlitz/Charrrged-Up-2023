package edu.greenblitz.pegasus.commands.funnel;

import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.greenblitz.pegasus.subsystems.Funnel;

public abstract class FunnelCommand extends GBCommand {

	protected Funnel funnel;

	public FunnelCommand() {
		funnel = Funnel.getInstance();
		require(funnel);
	}
}
