package edu.greenblitz.utils.SystemCheck;

import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

import javax.swing.plaf.PanelUI;
import java.util.HashMap;

public class SystemCheck {

    private HashMap<GBSubsystem, Command> subsystemsAndCommands;
    private static SystemCheck instance;

    public static SystemCheck getInstance(){
        if (instance == null){
            instance = new SystemCheck();
        }
        return instance;
    }

    public SystemCheck(){
        subsystemsAndCommands = new HashMap<>();
    }

    public void add (GBSubsystem subsystem,Command command){
        subsystemsAndCommands.putIfAbsent(subsystem, command);
    }

    public void remove (GBSubsystem subsystem,Command command){
        subsystemsAndCommands.remove(subsystem,command);
    }

    public Command getCommandForSubsystem(GBSubsystem subsystem){
        return subsystemsAndCommands.get(subsystem);
    }
    public GBSubsystem[] getSubsystems (){
        return subsystemsAndCommands.keySet().toArray(new GBSubsystem[0]);
    }

}
