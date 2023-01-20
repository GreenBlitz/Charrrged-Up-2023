package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class LED extends GBSubsystem {

    private static LED instance;
    private AddressableLED addressableLED;
    private AddressableLEDBuffer ledBuffer;
    private Color defaultColor = Color.kGreen;

    private LED() {
        this.addressableLED = new AddressableLED(RobotMap.LED.PORT);
        this.ledBuffer = new AddressableLEDBuffer(RobotMap.LED.LENGTH);
        this.addressableLED.setLength(RobotMap.LED.LENGTH);
        this.addressableLED.start();
    }


    public static LED getInstance() {
        if (instance == null) {
            instance = new LED();
        }
        return instance;
    }

    public void setColor(Color color) {
        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setLED(i, color);
            SmartDashboard.putNumber("led num", i);
        }
        this.addressableLED.setData(ledBuffer);
    }

    public void setColor(int i, Color color) {
        this.ledBuffer.setLED(i, color);
        this.addressableLED.setData(ledBuffer);
    }

    public void setHSV(int h, int s, int v) {
        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setHSV(i, h, s, v);
            SmartDashboard.putNumber("led num", i);
        }
        this.addressableLED.setData(ledBuffer);
    }

    public void setSpecificLedColor(int n, Color color) {
        this.ledBuffer.setLED(n, color);
        this.addressableLED.setData(ledBuffer);
    }


    public void move(int cnt, Color color) {
        LED.getInstance().setSpecificLedColor(cnt % 60 == 0 ? 59 : cnt % 60 - 1, new Color(0, 0, 0));
        Timer.delay(0.01);
        LED.getInstance().setSpecificLedColor(cnt % 60, new Color(0, 255, 0));
        cnt++;
        SmartDashboard.putNumber("a", cnt % 60);
        Timer.delay(0.01);
    }

    public void setDefaultColor(Color color){
        this.defaultColor = color;
    }
    public Color getDefaultColor(){
        return defaultColor;
    }


}