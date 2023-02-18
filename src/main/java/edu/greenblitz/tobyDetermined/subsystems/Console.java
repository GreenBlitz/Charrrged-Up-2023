package edu.greenblitz.tobyDetermined.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Console {
    private static ShuffleboardLayout shuffleboardConsole;

    public static void log(String title, String message) {
        title = title + " : " + DriverStation.getMatchTime();
        shuffleboardConsole.add(title, message);
    }


    public static ShuffleboardLayout getShuffleboardConsole(ShuffleboardTab tab) {
        shuffleboardConsole = tab.getLayout("Console", BuiltInLayouts.kList);
        return shuffleboardConsole;
    }

}
