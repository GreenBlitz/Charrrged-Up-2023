package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.IO;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;

public class RotatingBellyIOTalonSRX implements RotatingBellyIO{


    private TalonSRX motor;
    private DigitalInput limitSwitch;
    public RotatingBellyIOTalonSRX(){

//        colorSensor = BellyGameObjectSensor.getInstance();
        motor = new TalonSRX(RobotMap.RotatingBelly.MOTOR_ID);



        motor.setInverted(true);
    }


    @Override
    public void setPower(double power){
        motor.set(TalonSRXControlMode.PercentOutput,power);
    }

    @Override
    public void stop() {
        setPower(0);
    }



    @Override
    public void updateInputs(RotatingBellyInputs inputs){
        inputs.motorVoltage = motor.getMotorOutputVoltage();
//        inputs.isSwitchPressed = limitSwitch.get();
    }

}
