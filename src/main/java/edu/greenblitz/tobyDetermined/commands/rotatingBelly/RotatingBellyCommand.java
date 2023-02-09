package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;
import edu.greenblitz.utils.GBCommand;

public abstract class RotatingBellyCommand extends GBCommand {
    protected RotatingBelly belly;

    public RotatingBellyCommand (){
        belly = RotatingBelly.getInstance();
        require(belly);
    }
}
