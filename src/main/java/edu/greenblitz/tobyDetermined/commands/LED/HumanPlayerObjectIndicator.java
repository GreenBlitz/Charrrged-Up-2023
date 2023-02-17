package edu.greenblitz.tobyDetermined.commands.LED;


import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

/**
 * @author noam - a command to indicate to the human player what object he wants (he - the robot)
 * */
public class HumanPlayerObjectIndicator extends LEDCommand{

    private final wantedObject wantedGameObject;

    public HumanPlayerObjectIndicator(wantedObject object){
        this.wantedGameObject = object;
    }

    public enum wantedObject{
        CUBE,
        CONE
    }

    @Override
    public void execute() {
        switch (this.wantedGameObject){
            case CONE:
                led.setBackgroundColor(Color.kYellow);

                new LedBlinking(Color.kYellow).schedule();
                break;
            case CUBE:
                led.setBackgroundColor(Color.kMagenta);

                new LedBlinking(Color.kMagenta).schedule();
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
