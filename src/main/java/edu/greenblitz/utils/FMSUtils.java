package edu.greenblitz.utils;

import edu.wpi.first.wpilibj.DriverStation;

import java.util.Optional;

public class FMSUtils {

    private static final DriverStation.Alliance DEFAULT_ALLIANCE = DriverStation.Alliance.Blue;
    public static DriverStation.Alliance getAlliance (){
        //default is blue:
        return DriverStation.getAlliance().orElse(DEFAULT_ALLIANCE);
    }

}
