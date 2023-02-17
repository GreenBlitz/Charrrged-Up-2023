package edu.greenblitz.tobyDetermined.commands.LED;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

public class BalanceOnRampLED extends ParallelRaceGroup {

    public BalanceOnRampLED(){
        addCommands(new LedBlinking(Color.kAqua));
    }
}
