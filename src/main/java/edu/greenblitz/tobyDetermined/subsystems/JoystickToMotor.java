package edu.greenblitz.tobyDetermined.subsystems;


import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.motors.GBSparkMax;

public class JoystickToMotor {

    private JoystickToMotor instance;
    private GBSparkMax motor;

    private JoystickToMotor() {
        motor = new GBSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    }
    public JoystickToMotor getInstance() {
        instance = new JoystickToMotor();
        return instance;
    }

    public double getX() {
        return OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X);
    }

    public void setSpeed(double speed) {
        motor.set(speed);
    }
}
