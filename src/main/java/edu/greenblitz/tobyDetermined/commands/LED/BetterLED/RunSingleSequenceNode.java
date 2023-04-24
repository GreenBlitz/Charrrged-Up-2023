package edu.greenblitz.tobyDetermined.commands.LED.BetterLED;

import edu.greenblitz.tobyDetermined.commands.LED.LEDCommand;
import edu.greenblitz.utils.InterruptableWaitCommand;
import edu.greenblitz.utils.LEDSequence;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RunSingleSequenceNode extends SequentialCommandGroup {

    private LEDSequence sequence;
    public RunSingleSequenceNode(LEDSequence sequence){
        this.sequence = sequence;
        addCommands(
                new InterruptableWaitCommand(sequence.getWaitBeforeStartTime()),
                new SimpleSetColor(sequence.getColor()),
                new InterruptableWaitCommand(sequence.getDuration())
        );
    }




}
