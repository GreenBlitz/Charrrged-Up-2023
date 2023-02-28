package edu.greenblitz.tobyDetermined.commands.LED;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;

public class BalanceOnRampLED extends SetLEDColor {

    public BalanceOnRampLED(){
        super(Color.kRed);
    }
}
