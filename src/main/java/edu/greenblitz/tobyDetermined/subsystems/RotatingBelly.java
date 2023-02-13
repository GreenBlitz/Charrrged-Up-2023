package edu.greenblitz.tobyDetermined.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;

public class RotatingBelly extends GBSubsystem{


    private static RotatingBelly instance;
    private TalonSRX motor;
    private DigitalInput limitSwitch;
    private IntakeGameObjectSensor colorSensor;

    private RotatingBelly (){
        limitSwitch = new DigitalInput(RobotMap.RotatingBelly.MACRO_SWITCH_PORT);
        colorSensor = IntakeGameObjectSensor.getInstance();
        motor = new TalonSRX(RobotMap.RotatingBelly.MOTOR_ID);
    }

    public void setPower(double power){
        motor.set(TalonSRXControlMode.PercentOutput,power);
    }

    public static RotatingBelly getInstance (){
        if(instance == null){
            instance = new RotatingBelly();
        }
        return instance;
    }

    public IntakeGameObjectSensor.GameObject getGameObject (){
        return colorSensor.getCurObject();
    }

    public boolean isLimitSwitchPressed(){
        return limitSwitch.get();
    }
    public void stop (){
        setPower(0);
    }

}
