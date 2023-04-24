package edu.greenblitz.tobyDetermined.commands.LED.BetterLED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.LEDSequence;

public class RunSequence extends GBCommand {

    private LEDSequence sequence;

    public RunSequence (LEDSequence sequence){
        this.sequence = sequence;
    }

    @Override
    public void execute() {
        if (sequence != null){
            new RunSingleSequenceNode(sequence);
            sequence = sequence.getNext();
        }
    }

    @Override
    public boolean isFinished() {
        return sequence == null;
    }

}
