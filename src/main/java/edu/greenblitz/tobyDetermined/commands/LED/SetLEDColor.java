package edu.greenblitz.tobyDetermined.commands.LED;

import edu.wpi.first.wpilibj.util.Color;

public class SetLEDColor extends LEDCommand{

    private Color color;
    public SetLEDColor(Color color){
        this.color = color;
    }


    @Override
    public void execute(){
        led.setColor(color);
    }

}
