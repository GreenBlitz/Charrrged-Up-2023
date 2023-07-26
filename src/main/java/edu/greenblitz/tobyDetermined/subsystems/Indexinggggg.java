package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ColorSensorV3;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.I2C;

import java.awt.*;

public class Indexinggggg extends GBSubsystem   {
    private ColorSensorV3 cs;
    private  Indexinggggg instance;
    private GBSparkMax motor;
    private Indexinggggg() {

        cs = new ColorSensorV3(I2C.Port.kOnboard);
        motor = new GBSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public Colors getBallColor() {
        float[] color = Color.RGBtoHSB(cs.getRed(), cs.getGreen(), cs.getBlue(), new float[3]);
        float[] color1 = {cs.getRed(),cs.getGreen(),cs.getBlue()};
//        if (color[0] >= 0.05 && color[0] <= 0.2) {
//            return 1; //1=red
//        }
        if(color1[0] > color1[1] && color1[0] > color1[2]) {
            return Colors.Red;
        }
        else if(color1[2] > color1[1] && color1[2] > color1[0]){
            return Colors.Blue;
        }

        return Colors.Other;
    }
    public Indexinggggg getInstance() {
        if (instance == null) {
            instance = new Indexinggggg();
        }
        return instance;
    }
    public void setSpeed(double speed)
    {
        this.motor.set(speed);
    }
}
