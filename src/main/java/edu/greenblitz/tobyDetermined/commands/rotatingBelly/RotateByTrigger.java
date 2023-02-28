package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.utils.hid.SmartJoystick;

public class RotateByTrigger extends RotatingBellyCommand{
    SmartJoystick joystick;
    public RotateByTrigger(SmartJoystick joystick){
        this.joystick = joystick;
    }

    @Override
    public void execute() {
        double power = joystick.getAxisValue(SmartJoystick.Axis.LEFT_TRIGGER) * 1;
        double minusPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_TRIGGER) * 1;
        belly.setPower(power - minusPower);
    }
}
