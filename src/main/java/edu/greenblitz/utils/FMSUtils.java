package edu.greenblitz.utils;

import edu.wpi.first.wpilibj.DriverStation;

public class FMSUtils {


    public static boolean isRealMatch(){
        return
                DriverStation.getMatchType() == DriverStation.MatchType.Elimination ||
                DriverStation.getMatchType() == DriverStation.MatchType.Qualification;
    }

    public static DriverStation.Alliance getAlliance (){
        return DriverStation.getAlliance();
    }
    public static int getMatchNumber (){
        return DriverStation.getMatchNumber();
    }

}
