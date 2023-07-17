package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class LED extends GBSubsystem {

    private static LED instance;
    private AddressableLED addressableLED;
    private AddressableLEDBuffer ledBuffer;

    private LED() {
        this.addressableLED = new AddressableLED(RobotMap.LED.PORT);
        this.ledBuffer = new AddressableLEDBuffer(RobotMap.LED.LENGTH);
        this.addressableLED.setLength(RobotMap.LED.LENGTH);
        this.addressableLED.start();
    }


    public static LED getInstance() {
        init();
        return instance;
    }

    public static void init(){
        if (instance == null) {
            instance = new LED();
        }
    }


    public void setColor (Color color){
        setColor(color, RobotMap.LED.Sections.ALL);
    }
    public void setColor(Color color, int index) {
        this.ledBuffer.setLED(index, color);
    }

    public void setColor ( Color color,int startIndex,int endIndex){
        for (int i = startIndex; i < endIndex; i++) {
            setColor(color,i);
        }
    }

    public void setColor (Color color, RobotMap.LED.Sections section){
        setColor(color,section.start,section.end);
    }

    public void turnOff (){
        setColor(new Color(0,0,0),RobotMap.LED.Sections.ALL);
    }

    public void turnOff (int index){
        setColor(new Color(0,0,0),index);
    }
    public void turnOff (int startIndex,int endIndex){
        for (int i = startIndex; i < endIndex; i++) {
            turnOff(i);
        }
    }
    public void turnOff (RobotMap.LED.Sections section){
        turnOff(section.start,section.end);
    }



    public void setHSV(int h, int s, int v) {
        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setHSV(i, h, s, v);
        }
    }


    public void setHSV(int index, int h, int s, int v) {
        this.ledBuffer.setHSV(index, h, s, v);
    }

    @Override
    public void periodic() {
        this.addressableLED.setData(ledBuffer);
    }
}