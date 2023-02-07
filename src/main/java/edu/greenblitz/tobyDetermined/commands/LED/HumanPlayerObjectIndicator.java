package edu.greenblitz.tobyDetermined.commands.LED;


import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

/**
 * @author noam - a command to indicate to the human player what object he wants (he - the robot)
 * */
public class HumanPlayerObjectIndicator extends LEDCommand{

    private wantedObject object;

    public HumanPlayerObjectIndicator(wantedObject object){
        this.object = object;
    }

    public enum wantedObject{
        CUBE,
        CONE;
    }

    @Override
    public void initialize() {
        switch (this.object){
            case CONE:
                led.setBackgroundColor(Color.kYellow);

                led.setColor(Color.kYellow);
                Timer.delay(RobotMap.LED.BLINKING_ON_TIME);
                led.turnOff();
                Timer.delay(RobotMap.LED.BLINKING_OFF_TIME);
                led.setColor(Color.kYellow);
                Timer.delay(RobotMap.LED.BLINKING_ON_TIME);
                break;
            case CUBE:
                led.setBackgroundColor(Color.kMagenta);

                led.setColor(Color.kMagenta);
                Timer.delay(RobotMap.LED.BLINKING_ON_TIME);
                led.turnOff();
                Timer.delay(RobotMap.LED.BLINKING_OFF_TIME);
                led.setColor(Color.kMagenta);
                Timer.delay(RobotMap.LED.BLINKING_ON_TIME);
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
