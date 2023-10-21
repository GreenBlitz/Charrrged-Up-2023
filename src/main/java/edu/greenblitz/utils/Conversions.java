package edu.greenblitz.utils;

import edu.greenblitz.tobyDetermined.RobotMap;

public class Conversions {



    public static class MK4IConversions{
        public static double convertRadsToTicks(double angInRads) {
            return angInRads / RobotMap.Swerve.SdsSwerve.angleTicksToRadians;
        }

        public static double convertTicksToRads(double angInTicks) {
            return angInTicks * RobotMap.Swerve.SdsSwerve.angleTicksToRadians;
        }

        public static double convertMetersToTicks(double distanceInMeters) {
            return distanceInMeters / RobotMap.Swerve.SdsSwerve.linTicksToMeters;
        }

        public static double convertTicksToMeters(double angInTicks){
            return angInTicks * RobotMap.Swerve.SdsSwerve.linTicksToMeters;
        }

        public static double convertMetersPerSecondToTicksPer100ms(double mps){
            return mps/RobotMap.Swerve.SdsSwerve.linTicksToMeters;
        }
    }






}
