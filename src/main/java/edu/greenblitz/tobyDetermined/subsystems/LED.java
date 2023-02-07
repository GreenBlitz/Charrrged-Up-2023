package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class LED extends GBSubsystem {

    private static LED instance;
    private AddressableLED addressableLED;
    private AddressableLEDBuffer ledBuffer;
    private Color backgroundColor;

    private LED() {
        this.backgroundColor = RobotMap.LED.DEFAULT_COLOR;
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

    public void turnOff (){
            setColor(new Color(0,0,0));
    }

    public void turnOff (int index){
        setColor(index,new Color(0,0,0));
    }
    public void turnoff (int startIndex,int endIndex){
        for (int i = startIndex; i < endIndex; i++) {
            setColor(i,new Color(0,0,0));
        }
    }

    public void setColor(int i, Color color) {
        this.ledBuffer.setLED(i, color);
        this.addressableLED.setData(ledBuffer);
    }

    public void setHSV(int h, int s, int v) {
        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setHSV(i, h, s, v);
        }
        this.addressableLED.setData(ledBuffer);
    }


    public void setHSV(int index, int h, int s, int v) {
        this.ledBuffer.setHSV(index, h, s, v);
        this.addressableLED.setData(ledBuffer);
    }


    public void setBackgroundColor(Color color){
        this.backgroundColor = color;
    }
    public Color getBackgroundColor(){
        return backgroundColor;
    }
}