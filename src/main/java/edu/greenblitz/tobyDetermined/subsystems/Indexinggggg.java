package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.ColorSensorV3;
public class Indexinggggg extends GBSubsystem   {
    private ColorSensorV3 cs;
    public static Indexinggggg instance;
    private Indexinggggg() {
        cs = new ColorSensorV3(I2C.Port.kOnboard);
    }

    public DriverStation.Alliance getBallColor() {
        float[] color = Color.RGBtoHSB(cs.getRed(), cs.getGreen(), cs.getBlue(), new float[3]);
        if (color[0] >= 0.05 && color[0] <= 0.2) {
            return DriverStation.Alliance.Red;
        }
        else{

        }

    public static Indexinggggg getInstance() {
        if (instance == null) {
            instance = new Indexinggggg();
        }
        return instance;
    }

}
