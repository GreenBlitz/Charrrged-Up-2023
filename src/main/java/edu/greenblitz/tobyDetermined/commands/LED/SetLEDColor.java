package edu.greenblitz.tobyDetermined.commands.LED;

import edu.wpi.first.wpilibj.util.Color;

public class SetLEDColor extends LEDCommand{

    private Color color;
    private int index;

    public SetLEDColor(Color color){
        this.color = color;
    }
    public SetLEDColor(int index, Color color){
        this.color = color;
        this.index = index;
    }

    @Override
    public void execute(){
        led.setColor(color);
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }
}
