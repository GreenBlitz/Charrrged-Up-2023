package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;

public class RotatingBelly extends GBSubsystem {


    private static RotatingBelly instance;
    private TalonSRX motor;
    private DigitalInput limitSwitch;
    private BellyGameObjectSensor colorSensor;

    private RotatingBelly (){
        limitSwitch = new DigitalInput(RobotMap.RotatingBelly.MACRO_SWITCH_PORT);
        colorSensor = BellyGameObjectSensor.getInstance();
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

    public BellyGameObjectSensor.GameObject getGameObject (){
        return colorSensor.getCurObject();
    }

    public void setObjectToCone(){
        colorSensor.changeObjectToCone();
    }

    public void setObjectToCube(){
        colorSensor.changeObjectToCube();
    }
    public boolean isObjectIn(){
        return getGameObject() == BellyGameObjectSensor.GameObject.NONE;
    }

    public boolean isConeIn(){
        return getGameObject() == BellyGameObjectSensor.GameObject.CONE;
    }

    public boolean isLimitSwitchPressed(){
        return limitSwitch.get();
    }

    public void stop (){
        setPower(0);
    }

}
