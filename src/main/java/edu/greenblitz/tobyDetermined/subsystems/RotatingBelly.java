package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;

public class RotatingBelly extends GBSubsystem{


    private static RotatingBelly instance;
    private GBSparkMax motor;
    private DigitalInput macroSwitch;
    private IntakeGameObjectSensor colorSensor;

    private RotatingBelly (){
        macroSwitch = new DigitalInput(RobotMap.RotatingBelly.MACRO_SWITCH_PORT);
        colorSensor = IntakeGameObjectSensor.getInstance();
        motor = new GBSparkMax(RobotMap.RotatingBelly.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushed);
    }

    public void setSpeed (double percentageSpeed){
        motor.set(percentageSpeed);
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

    public boolean isMacroSwitchPressed (){
        return macroSwitch.get();
    }
    public void stop (){
        motor.set(0);
    }

}
