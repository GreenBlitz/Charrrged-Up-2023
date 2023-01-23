package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

import java.awt.*;

public class DefaultColor extends LEDCommand {


    public int cnt;


    public DefaultColor() {
    }

    @Override
    public void initialize() {
        cnt = 0;
    }

    @Override
    public void execute() {

        led.setColor(new Color (cnt,100,100));
        cnt++;
        if(cnt > RobotMap.LED.LENGTH){
            cnt = 0;
        }

    }
}
