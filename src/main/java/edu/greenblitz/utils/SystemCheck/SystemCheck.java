package edu.greenblitz.utils.SystemCheck;

import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.Limelight.MultiLimelight;
import edu.greenblitz.utils.PressureSensor;
import edu.greenblitz.utils.RoborioUtils;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class SystemCheck extends GBSubsystem {

    private static SystemCheck instance;
    private SequentialCommandGroup commandGroup;
    private PressureSensor pressureSensor;

    private ShuffleboardTab tab;
    private double innerBatteryResistance;
    private double startingVoltage;
    private double startingPressure;

    private GenericEntry batteryStartingVoltageEntry;

    private boolean isLimeLightConnected;


    public static SystemCheck getInstance() {
        if (instance == null) {
            instance = new SystemCheck();
        }
        return instance;
    }

    public SystemCheck() {


        this.innerBatteryResistance = calculateInnerBatteryResistance();
        this.startingVoltage = 13.73;

        this.pressureSensor = new PressureSensor(3);
        this.startingPressure = pressureSensor.getPressure();

        this.commandGroup = new SequentialCommandGroup();

        addSpecialChecks();
        initDashBoard();
        initPingableDashboard();

    }

    public void addSpecialChecks() {
        addPressureCheck();
        this.isLimeLightConnected = MultiLimelight.getInstance().isConnected();
    }

    public void addPressureCheck() {
        addToSeqCommand(new SequentialCommandGroup(
                new InstantCommand(
                        () -> startingPressure = pressureSensor.getPressure()
                ),
                new WaitCommand(Constants.AIR_LEAK_MEASUREMENT_TIME),
                new InstantCommand(
                        new Runnable() {
                            @Override
                            public void run() {
                                tab.addBoolean(
                                        "is pressure kept",
                                        () -> startingPressure - pressureSensor.getPressure() < Constants.MAX_AIR_PRESSURE_DROP_IN_TESTS);

                            }
                        }
                )
        ));
    }

    @Override
    public void periodic() {
        updateStartingVoltage(batteryStartingVoltageEntry.getDouble(13)); //by shuffle-board input
    }


    private void initDashBoard() {
        this.tab = Shuffleboard.getTab("System check");

        this.tab.addNumber("pressure", () -> pressureSensor.getPressure()).withPosition(1, 5);
        this.tab.addBoolean("is LL connected", () -> this.isLimeLightConnected);
        initBatteryWidget();
        initCANWidget();

    }

    private void initCANWidget() {
        ShuffleboardLayout CANDataList = tab.getLayout("CANBus", BuiltInLayouts.kGrid)
                .withPosition(1, 0).withSize(2, 2).withProperties(Map.of("Label position", "TOP", "Number of columns", 2, "Number of rows", 2));

        CANDataList.addBoolean("is CAN Connected", RoborioUtils::isCANConnectedToRoborio)
                .withPosition(0, 0);

        CANDataList.addBoolean("is CAN utilization high:", this::isCANUtilizationHigh)
                .withPosition(0, 1);

        CANDataList.addDouble("CAN utilization %", RoborioUtils::getCANUtilization)
                .withPosition(1, 0);
    }


    private void initBatteryWidget() {

        batteryStartingVoltageEntry = tab.add("set starting voltage", 13/*default value*/)
                .withWidget(BuiltInWidgets.kTextView)
                .getEntry();


        ShuffleboardLayout batteryDataList = tab.getLayout("System check", BuiltInLayouts.kList)
                .withPosition(0, 0).withSize(1, 5).withProperties(Map.of("Label position", "TOP", "Number of columns", 1, "Number of rows", 5));


        batteryDataList.addDouble("current voltage", () -> Battery.getInstance().getCurrentVoltage())
                .withPosition(0, 0);
        batteryDataList.addDouble("current current", () -> Battery.getInstance().getCurrentUsage())
                .withPosition(0, 1);
        batteryDataList.addDouble("battery inner resistance:", () -> getInnerBatteryResistance())
                .withPosition(0, 2);
        batteryDataList.addDouble("battery voltage drop:", () -> getVoltageDrop())
                .withPosition(0, 3);
        batteryDataList.addBoolean("battery overall good?",
                () -> getInnerBatteryResistance() < Constants.BatteryConstants.MAX_INNER_BATTERY_RESISTANCE &&
                        getVoltageDrop() < Constants.BatteryConstants.MAX_VOLTAGE_DROP
        ).withPosition(0, 4);


    }

    public void initPingableDashboard() {
        ShuffleboardLayout pingableDataList = tab.getLayout("pingable", BuiltInLayouts.kList)
                .withPosition(6, 0).withSize(PingableManager.getInstance().getPingableList().toArray().length/4, PingableManager.getInstance().getPingableList().toArray().length + 1)
                .withProperties(Map.of("Label position", "TOP",
                        "Number of columns",  PingableManager.getInstance().getPingableList().toArray().length / 8 + 1,
                        "Number of rows", PingableManager.getInstance().getPingableList().toArray().length));

        int columns = 1;
        for (IPingable pingable : PingableManager.getInstance().getPingableList()) {
            pingableDataList.addBoolean(pingable.deviceName(),() -> pingable.isConnected()).withPosition(columns / 4, columns);
            columns++;
        }


    }


    public double getInnerBatteryResistance() {
        this.innerBatteryResistance = calculateInnerBatteryResistance();
        return innerBatteryResistance;
    }

    public double getStartingVoltage() {
        return startingVoltage;
    }


    private double calculateInnerBatteryResistance() {
        return ((this.startingVoltage - Battery.getInstance().getCurrentVoltage()) / Battery.getInstance().getCurrentUsage());
    }

    public void add(Command command, BooleanSupplier isAtTargetSupplier, String subsystems) {
        addToSeqCommand(command);
        this.tab.addBoolean(subsystems, isAtTargetSupplier);
    }

    public Command getRunCommands() {

        return this.commandGroup;
    }

    public boolean isCANUtilizationHigh() {
        return RoborioUtils.getCANUtilization() > Constants.MAX_CAN_UTILIZATION_IN_TESTS;
    }


    private void addToSeqCommand(Command command) {
        this.commandGroup.addCommands(command);
    }


    private void updateStartingVoltage(double startingVoltage) {
        this.startingVoltage = startingVoltage;

    }


    public double getVoltageDrop() {
        return SystemCheck.getInstance().getStartingVoltage() - Battery.getInstance().getCurrentVoltage();
    }


    public static class Constants {
        public static final double MAX_CAN_UTILIZATION_IN_TESTS = 70;
        public static final double MAX_AIR_PRESSURE_DROP_IN_TESTS = 70;
        public static final double AIR_LEAK_MEASUREMENT_TIME = 3;


        public static final class BatteryConstants {
            public static final double MAX_INNER_BATTERY_RESISTANCE = 0;
            public static final double MAX_VOLTAGE_DROP = 0;

        }

    }

}
