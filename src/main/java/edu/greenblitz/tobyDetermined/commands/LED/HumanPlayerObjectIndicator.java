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
                new LedBlinking(Color.kYellow).repeatedly();
                break;

            case CUBE:
                new LedBlinking(Color.kMagenta).repeatedly();
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
