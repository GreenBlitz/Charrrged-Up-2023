package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.SystemCheck.SystemCheck;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SystemCheckCommand extends GBCommand {




    public SystemCheckCommand(){


        if(SystemCheck.getInstance().getStartingVoltage() -
                Battery.getInstance().getCurrentVoltage() >= RobotMap.General.SystemCheckConstants.MAX_BATTERY_VOLTAGE_DROP_UNDER_LOAD){
            //battery voltage drop to high
        }



        SequentialCommandGroup checkCommands = new SequentialCommandGroup();

        for (GBSubsystem subsystem : SystemCheck.getInstance().getSubsystems()){
            checkCommands.addCommands(SystemCheck.getInstance().getCheckCommandForSubsystem(subsystem).getRunCommand());
        }


        new ParallelCommandGroup(
                new ConditionalCommand(  //check if battery voltage drops below thresh hold
                        new ParallelCommandGroup(//if Voltage is less than min
                                new InstantCommand(()-> LED.getInstance().setColor(1,Color.kRed)),
                                new InstantCommand(() -> end(true))
                        ),
                        new InstantCommand(() -> end(false)),
                        ()-> (Battery.getInstance().getCurrentVoltage() < RobotMap.General.SystemCheckConstants.MIN_VOLTAGE_BATTERY &&
                                Battery.getInstance().getCurrentVoltage() >= RobotMap.General.SystemCheckConstants.MAX_BATTERY_VOLTAGE_DROP_UNDER_LOAD)
                ),checkCommands
                );






    }


}
