package edu.greenblitz.tobyDetermined.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class LED extends GBSubsystem{

    private static final int PORT = 0;
    private static LED instance;
    private static int ledLength = 60;
    private AddressableLED addressableLED;
    private AddressableLEDBuffer ledBuffer;
    private LED (){
        this.addressableLED = new AddressableLED(PORT);
        this.ledBuffer = new AddressableLEDBuffer(ledLength);
        this.addressableLED.setLength(ledBuffer.getLength());
        this.addressableLED.start();
        setColor(new Color(255, 255, 255));
    }


    public static LED getInstance(){
        if(instance == null){
            instance = new LED();
        }
        return instance;
    }

    public void setColor (Color color){
        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setLED(i,color);
            SmartDashboard.putNumber("led num",i);
        }
        this.addressableLED.setData(ledBuffer);
    }
    public void setColor (int i ,Color color){
        this.ledBuffer.setLED(i,color);
        this.addressableLED.setData(ledBuffer);
    }
    
    public void setHSV (int h, int s, int v){
        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setHSV(i,h, s, v);
            SmartDashboard.putNumber("led num",i);
        }
        this.addressableLED.setData(ledBuffer);
    }
    public void setSpecificLedColor(int n,Color color){
        this.ledBuffer.setLED(n,color);
        this.addressableLED.setData(ledBuffer);
    }
    
    public void rainbow (){
        for (int i = 0; true; i = i + 1 % 255) {
            setHSV(100,100,i);
        }
    }
    public void setGradiant (Color startingColor,Color endColor){
        Color current = new Color(startingColor.red,startingColor.green,startingColor.blue);
        for (int i = 0; i < ledLength; i++) {
            setColor(i,current);
            current = new Color(current.red+1,current.green+1,current.blue+1);
        }
    }
    
    public void move
    public void move (int cnt , Color color){
        LED.getInstance().setSpecificLedColor(cnt % 60 == 0 ? 59:cnt%60-1,new Color(0,0,0));
        Timer.delay(0.01);
        LED.getInstance().setSpecificLedColor(cnt % 60,new Color(0,255,0));
        cnt++;
        SmartDashboard.putNumber("a",cnt % 60);
        Timer.delay(0.01);
    }
}