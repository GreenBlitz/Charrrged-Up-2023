package edu.greenblitz.tobyDetermined.commands.Auto.balance.bangBangBalance;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullBalance extends SequentialCommandGroup {

    public static final double DEFAULT_SPEED = 1;

    public FullBalance(double speed) {
        super(new GetToRamp(speed * RobotMap.Swerve.Balance.GET_ON_SPEED_FACTOR), new GetOnRamp(speed * RobotMap.Swerve.Balance.GET_ON_SPEED_FACTOR), new BangBangBalance(speed * RobotMap.Swerve.Balance.BANG_BANG_SPEED_FACTOR));
    }

    public FullBalance(boolean forward) {
        this(forward ? DEFAULT_SPEED : -DEFAULT_SPEED);
    }
}
