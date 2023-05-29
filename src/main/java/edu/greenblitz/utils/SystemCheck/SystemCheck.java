package edu.greenblitz.utils.SystemCheck;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.jetbrains.annotations.NotNull;
import scala.collection.parallel.immutable.ParRange;

import javax.annotation.Nonnull;
import javax.swing.plaf.PanelUI;
import java.util.HashMap;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class SystemCheck extends GBSubsystem{

    private HashMap<GBSubsystem, CheckCommand> subsystemsAndCommands;
    private static SystemCheck instance;


    private ShuffleboardTab tab;
    private double innerBatteryResistance;
    private double startingVoltage;
    public static SystemCheck getInstance(){
        if (instance == null){
            instance = new SystemCheck();
        }
        return instance;
    }

    public SystemCheck(){
        this.tab = Shuffleboard.getTab("System check");


        this.innerBatteryResistance = calculateInnerBatteryResistance();
        this.startingVoltage = 13.73;

        subsystemsAndCommands = new HashMap<>();


        tab.addDouble("current voltage", ()-> Battery.getInstance().getCurrentVoltage());
        tab.addDouble("current current", ()-> Battery.getInstance().getCurrentUsage());
        tab.addDouble("battery inner resistance:", () -> getInnerBatteryResistance());
        tab.addDouble("battery voltage drop:", () ->  SystemCheck.getInstance().getStartingVoltage() - Battery.getInstance().getCurrentVoltage());

    }

    
    public double getInnerBatteryResistance() {
        this.innerBatteryResistance  = calculateInnerBatteryResistance();
        return innerBatteryResistance;
    }

    public double getStartingVoltage() {
        return startingVoltage;
    }


    private double calculateInnerBatteryResistance (){
        return ((this.startingVoltage - Battery.getInstance().getCurrentVoltage()) / Battery.getInstance().getCurrentUsage());
    }

    public void add (CheckCommand checkCommand, GBSubsystem subsystem){
        subsystemsAndCommands.putIfAbsent(subsystem, checkCommand);
        this.tab.addBoolean(subsystem.getClass().getName(),checkCommand.getBooleanSupplier());
    }
    public void add (CheckCommand checkCommand, @NotNull GBSubsystem... subsystems){
        //in the multi subsystem case the first inserted subsystem is the keyholder.
        String multiSystemName = "";
        GBSubsystem keyHolder = null;
        for (GBSubsystem subs : subsystems){

            if(keyHolder == null){
                keyHolder = subs;
            }

            multiSystemName += subs.getClass().getSimpleName() + ", ";
        }

        subsystemsAndCommands.putIfAbsent(keyHolder, checkCommand);
        this.tab.addBoolean(multiSystemName.getClass().getName(),checkCommand.getBooleanSupplier());
    }

    public void add (String overrideName,CheckCommand checkCommand, @NotNull GBSubsystem... subsystems){
        //in the multi subsystem case the first inserted subsystem is the keyholder.
        String multiSystemName = "";
        GBSubsystem keyHolder = null;
        for (GBSubsystem subs : subsystems){
            if(keyHolder == null){
                keyHolder = subs;
            }
        }

        subsystemsAndCommands.putIfAbsent(keyHolder, checkCommand);
        this.tab.addBoolean(overrideName,checkCommand.getBooleanSupplier());
    }

    public void remove (GBSubsystem subsystem,Command command){
        subsystemsAndCommands.remove(subsystem,command);
    }

    public CheckCommand getCheckCommandForSubsystem(GBSubsystem subsystem){
        return subsystemsAndCommands.get(subsystem);
    }
    public GBSubsystem[] getSubsystems (){
        return subsystemsAndCommands.keySet().toArray(new GBSubsystem[0]);
    }


    public void runCommands (){
        SequentialCommandGroup group = new SequentialCommandGroup();
        for (GBSubsystem subsystem : getSubsystems()){
            group.addCommands(getCheckCommandForSubsystem(subsystem).getRunCommand());
        }
        group.schedule();
    }

}
