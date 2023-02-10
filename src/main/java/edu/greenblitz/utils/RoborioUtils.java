package edu.greenblitz.utils;

public class RoborioUtils {
    private static long lastTime;
    private static long currentTime;

    public static void updateCurrentCycleTime(){
        lastTime = currentTime;
        currentTime = System.currentTimeMillis();
    }

    public static double getCurrentRoborioCycle (){
        return lastTime - currentTime;
    }
}
