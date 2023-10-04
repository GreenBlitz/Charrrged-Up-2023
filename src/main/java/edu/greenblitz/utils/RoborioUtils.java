package edu.greenblitz.utils;

import edu.wpi.first.hal.can.CANJNI;
import edu.wpi.first.hal.can.CANStatus;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;

public class RoborioUtils {
    private static double lastTime;
    private static double currentTime;

    public static void updateCurrentCycleTime(){
        lastTime = currentTime;
        currentTime = Timer.getFPGATimestamp();
    }

    public static double getCurrentRoborioCycle (){
        return lastTime - currentTime;
    }

    public static boolean isCANConnectedToRoborio(){
         return getCANUtilization() > 0; //if anything uses CAN the utilization will be bigger than 0.
    }

    public static double getCANUtilization(){
        return  (RobotController.getCANStatus().percentBusUtilization)*100;
    }


}
