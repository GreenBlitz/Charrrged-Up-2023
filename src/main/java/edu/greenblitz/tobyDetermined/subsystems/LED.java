package edu.greenblitz.tobyDetermined.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class LED extends GBSubsystem {

    private static final int PORT = 0;
    private static LED instance;
    private static int ledLength = 60;
    private AddressableLED addressableLED;
    private AddressableLEDBuffer ledBuffer;
    private Color defaultColor = Colors.white.color;

    private LED() {
        this.addressableLED = new AddressableLED(PORT);
        this.ledBuffer = new AddressableLEDBuffer(ledLength);
        this.addressableLED.setLength(ledBuffer.getLength());
        this.addressableLED.start();
        setColor(new Color(255, 255, 255));
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
    public void setColor (Colors color){
        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setLED(i, color.color);
            SmartDashboard.putNumber("led num", i);
        }
        this.addressableLED.setData(ledBuffer);
    }

    public void setDefaultColor(Color color){
        this.defaultColor = color;
    }

    public enum Colors {
        red(new Color(255, 0, 0)),
        blue(new Color(0, 0, 255)),
        green(new Color(0, 255, 0)),
        yellow(new Color(255, 255, 0)),
        purple(new Color(230, 0, 250)),
        none (new Color(0,0,0)),
        white (new Color(255,255,255));
        private final Color color;

        Colors(Color color) {
            this.color = color;
        }

        Color getColor() {
            return color;
        }
    }
}