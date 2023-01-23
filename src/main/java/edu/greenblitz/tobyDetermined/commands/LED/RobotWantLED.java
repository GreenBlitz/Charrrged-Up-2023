package edu.greenblitz.tobyDetermined.commands.LED;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

/**
 * @author noam - a command to indicate to the human player what object he wants (he - the robot)
 * */
public class RobotWantLED extends LEDCommand{

    private wantedObject object;

    public RobotWantLED(wantedObject object){
        this.object = object;
    }

    public enum wantedObject{
        cube,cone;
    }

    @Override
    public void initialize() {
        switch (this.object){
            case cone:
                led.setColor(Color.kYellow);
                Timer.delay(0.4);
                led.setColor(Color.kWhite);
                Timer.delay(0.2);
                led.setColor(Color.kYellow);
                Timer.delay(0.4);
                break;
            case cube:
                led.setColor(Color.kMagenta);
                Timer.delay(0.4);
                led.setColor(Color.kWhite);
                Timer.delay(0.2);
                led.setColor(Color.kMagenta);
                Timer.delay(0.4);
        }
    }
}
