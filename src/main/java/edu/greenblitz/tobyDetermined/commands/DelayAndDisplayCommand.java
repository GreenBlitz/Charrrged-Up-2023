package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import javax.swing.*;

/**
 * @author noam
 * @do showing a true on the dashboard when the command runs
 * */

public class DelayAndDisplayCommand extends GBCommand {

    private double timeInSec;
    private String tag;

    public DelayAndDisplayCommand (String toPut, double timeInSeconds){
        timeInSec =timeInSeconds;
        tag = toPut;
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean(tag,true);
        new WaitCommand(timeInSec).schedule();
        SmartDashboard.putBoolean(tag,false);
    }
}
