package edu.greenblitz.utils.SystemCheck;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.function.BooleanSupplier;

public class CheckCommand {

    private BooleanSupplier isAtTargetSupplier;
    private Command runCommand;

    private double timeout;

    public CheckCommand(Command runCommand, BooleanSupplier isAtTargetSupplier, double timeout) {
        this.isAtTargetSupplier = isAtTargetSupplier;
        this.timeout = timeout;
        this.runCommand = runCommand.raceWith(new WaitCommand(this.timeout));

    }

    public CheckCommand(Command runCommand, BooleanSupplier isAtTargetSupplier) {

        this.timeout = RobotMap.General.SystemCheckConstants.DEFAULT_TIMEOUT_FOR_CHECK_COMMAND;

        this.isAtTargetSupplier = isAtTargetSupplier;
        this.runCommand = runCommand.raceWith(new WaitCommand(this.timeout));
    }

    public boolean IsAtTarget() {
        return isAtTargetSupplier.getAsBoolean();
    }
    public BooleanSupplier getBooleanSupplier (){
        return this.isAtTargetSupplier;
    }

    public Command getRunCommand() {
        return runCommand;
    }
}
