package edu.greenblitz.tobyDetermined.commands.LED;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;

public class BalanceOnRampLED extends RepeatCommand {

    public BalanceOnRampLED(){
        super(new LedBlinking(Color.kAqua));
    }

}
