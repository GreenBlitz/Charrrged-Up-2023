package edu.greenblitz.tobyDetermined.commands.indicatingCommands;

import edu.greenblitz.tobyDetermined.commands.LED.BalanceOnRampLED;
import edu.greenblitz.tobyDetermined.commands.swerve.AdvancedBalanceOnRamp;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

public class BalanceOnRampWithIndicatorForward extends ParallelRaceGroup {
    public BalanceOnRampWithIndicatorForward(boolean isForward){
        super(new AdvancedBalanceOnRamp(isForward), new BalanceOnRampLED());
    }
}
