package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.greenblitz.utils.GBCommand;

public abstract class LEDCommand extends GBCommand {
    protected LED led;

    public LEDCommand(){
        led = LED.getInstance();
        require(led);
    }

    @Override
    public void end(boolean interrupted) {
        this.cancel();
    }
}
