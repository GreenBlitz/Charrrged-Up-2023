package edu.greenblitz.tobyDetermined.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class LED extends GBSubsystem{

    private static final int PORT = 9;
    private static LED instance;
    private AddressableLED addressableLED;
    private AddressableLEDBuffer ledBuffer;
    private LED (){
        this.addressableLED = new AddressableLED(PORT);
        this.ledBuffer = new AddressableLEDBuffer(60);
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
    }




}