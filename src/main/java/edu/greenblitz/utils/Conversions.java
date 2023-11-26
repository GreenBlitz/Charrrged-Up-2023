package edu.greenblitz.utils;

import edu.greenblitz.tobyDetermined.RobotMap;

public class Conversions {

    public static double convertRPMToRadsPerSec (double rpm){
        return rpm * Math.PI / 30; //thats da formula dat i saw on stackoverflow - https://stackoverflow.com/questions/55562081/how-can-i-scale-a-velocity-value-from-native-units-ticks-per-100ms-to-meters-p
    }
    public static double convertRPMToMeterPerSecond (double rpm, double wheelRadius){
        return (2 * Math.PI * wheelRadius) / 60 * rpm;
    }

    public static class MK4IConversions{
        public static double convertRevolutionToMeters(double angInTicks){
            return angInTicks * RobotMap.Swerve.SdsSwerve.WHEEL_CIRC;
        }


        public static double revolutionsToMeters(double revolutions) {
            return revolutions * RobotMap.Swerve.SdsSwerve.WHEEL_CIRC;
        }
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

        public static double convertTicksPer100msToRPM(double ticksPer100ms){
            return ticksPer100ms * RobotMap.General.Motors.FALCON_VELOCITY_UNITS_PER_RPM;
        }

        public static double convertSensorVelocityToRPM(double ticks){
            return ticks * RobotMap.Swerve.SdsSwerve.angleTicksToWheelToRPM;
        }
        public static double convertSensorTicksToRadPerSecond(double ticks){
            return convertRPMToRadsPerSec(convertSensorVelocityToRPM(ticks));
        }
        public static double convertSensorVelocityToMeterPerSecond(double selectedSensorVelocity){
            return selectedSensorVelocity * RobotMap.Swerve.SdsSwerve.linTicksToMetersPerSecond;
        }
        public static double convertRPMToMeterPerSecond (double rpm){
            return Conversions.convertRPMToMeterPerSecond(rpm, RobotMap.Swerve.SdsSwerve.WHEEL_CIRC / (2 * Math.PI));
        }
    }






}
