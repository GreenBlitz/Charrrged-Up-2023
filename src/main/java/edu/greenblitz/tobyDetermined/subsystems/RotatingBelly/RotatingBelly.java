package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotatingBelly extends GBSubsystem {


    private static RotatingBelly instance;
    private TalonSRX motor;
    private DigitalInput limitSwitch;
//    private BellyGameObjectSensor colorSensor;

    private RotatingBelly (){
        limitSwitch = new DigitalInput(RobotMap.RotatingBelly.MACRO_SWITCH_DIO_PORT);
//        colorSensor = BellyGameObjectSensor.getInstance();
        motor = new TalonSRX(RobotMap.RotatingBelly.MOTOR_ID);

        motor.setInverted(true);
    }

    public void setPower(double power){
        motor.set(TalonSRXControlMode.PercentOutput,power);
    }

    public static RotatingBelly getInstance (){
        init();
        return instance;
    }

    public static void init(){
        if(instance == null){
            instance = new RotatingBelly();
        }
    }

    /*
    commented code since the color sensor doesn't exist yet
    public BellyGameObjectSensor.GameObject getGameObject (){
        return colorSensor.getCurObject();
    }

    public boolean isObjectIn(){
        return getGameObject() == BellyGameObjectSensor.GameObject.NONE;
    }

    public boolean isConeIn(){
        return getGameObject() == BellyGameObjectSensor.GameObject.CONE;
    }
*/
    public boolean isLimitSwitchPressed(){
        return !limitSwitch.get();
    }

    public void stop (){
        setPower(0);
    }


    @Override
    public void periodic() {
        SmartDashboard.putBoolean("is",RotatingBelly.getInstance().isLimitSwitchPressed());
    }
}
