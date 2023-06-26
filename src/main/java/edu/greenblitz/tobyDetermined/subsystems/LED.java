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

    public void setColor(int i, Color color) {
        this.ledBuffer.setLED(i, color);
    }

    public void setColor (int startIndex,int endIndex, Color color){
        for (int i = startIndex; i < endIndex; i++) {
            setColor(i,color);
        }
    }

    public void setColor (RobotMap.LED.Sections section, Color color){
        setColor(section.start,section.end,color);
    }

    public void turnOff (){
        setColor(RobotMap.LED.Sections.ALL,new Color(0,0,0));
    }

    public void turnOff (int index){
        setColor(index,new Color(0,0,0));
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