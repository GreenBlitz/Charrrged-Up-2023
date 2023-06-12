package edu.greenblitz.utils.SystemCheck;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBFalcon;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.jetbrains.annotations.NotNull;
import scala.collection.parallel.immutable.ParRange;

import javax.annotation.Nonnull;
import javax.swing.plaf.PanelUI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class SystemCheck extends GBSubsystem{

    private static SystemCheck instance;
    private SequentialCommandGroup commandGroup;


    @Override
    public void periodic() {
        SmartDashboard.putBoolean("aaa",Extender.getInstance().isAtLength() && Elbow.getInstance().isAtAngle()
                );
    }

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


        this.innerBatteryResistance = calculateInnerBatteryResistance();
        this.startingVoltage = 13.73;


        this.commandGroup = new SequentialCommandGroup();

        initDashBoard();

    }

    public void initDashBoard (){
        this.tab = Shuffleboard.getTab("System check");

        ShuffleboardLayout batteryDataList = tab.getLayout("System check", BuiltInLayouts.kList)
                .withPosition(0,0).withSize(1, 4).withProperties(Map.of("Label position", "TOP", "Number of columns", 1, "Number of rows", 4));;

        batteryDataList.addDouble("current voltage", ()-> Battery.getInstance().getCurrentVoltage())
                .withPosition(0,0);
        batteryDataList.addDouble("current current", ()-> Battery.getInstance().getCurrentUsage())
                .withPosition(0,1);
        batteryDataList.addDouble("battery inner resistance:", () -> getInnerBatteryResistance())
                .withPosition(0,2);
        batteryDataList.addDouble("battery voltage drop:", () ->  SystemCheck.getInstance()
                .getStartingVoltage() - Battery.getInstance().getCurrentVoltage()).withPosition(0,3);


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

    public void add (Command command, BooleanSupplier isAtTargetSupplier , String subsystems){
        addToSeqCommand(command);
        this.tab.addBoolean(subsystems,  isAtTargetSupplier);
    }

    public Command getRunCommands (){

        return this.commandGroup;
    }

    public boolean isCANBusConnected(){
        return RoborioUtils.isCANConnectedToRoborio();
    }

    private void addToSeqCommand (Command command){
        this.commandGroup.addCommands(command);
    }


}
