package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.SystemCheck.SystemCheck;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import javax.naming.ldap.PagedResultsResponseControl;

public class SystemCheckCommand extends GBCommand {


    private double innerBatteryResistance;
    private double startingVoltage;
    public SystemCheckCommand(){

        this.innerBatteryResistance = (this.startingVoltage - Battery.getInstance().getCurrentVoltage()) / Battery.getInstance().getCurrentUsage();


        if(this.innerBatteryResistance >= RobotMap.General.MAX_INNER_BATTERY_RESISTANCE){
            //battery inner resistance to high
        }
        if(Battery.getInstance().getCurrentVoltage() >= RobotMap.General.MAX_BATTERY_VOLTAGE_DROP_UNDER_LOAD){
            //battery voltage drop to high
        }



        this.startingVoltage = Battery.getInstance().getCurrentVoltage();

        SequentialCommandGroup checkCommands = new SequentialCommandGroup();
        for (GBSubsystem subsystem : SystemCheck.getInstance().getSubsystems()){
            checkCommands.addCommands(SystemCheck.getInstance().getCommandForSubsystem(subsystem));
        }

        new ParallelCommandGroup(

                new ConditionalCommand(  //check if battery voltage drops below thresh - hold
                        new ParallelCommandGroup(//if Voltage is less than min
                                new InstantCommand(() -> end(true)),
                                new InstantCommand(()-> LED.getInstance().setColor(1,Color.kRed))
                        ),
                        new InstantCommand(() -> end(false)),
                        ()-> (Battery.getInstance().getCurrentVoltage() < RobotMap.General.MIN_VOLTAGE_BATTERY &&
                                Battery.getInstance().getCurrentVoltage() >= RobotMap.General.MAX_BATTERY_VOLTAGE_DROP_UNDER_LOAD)
                ),
                checkCommands
        );

    }


}
