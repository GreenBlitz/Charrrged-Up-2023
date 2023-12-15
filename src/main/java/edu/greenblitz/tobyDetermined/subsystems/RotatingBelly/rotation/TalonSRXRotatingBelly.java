//package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.rotation;
//
//import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import edu.greenblitz.tobyDetermined.RobotMap;
//import edu.wpi.first.wpilibj.DigitalInput;
//
//public class TalonSRXRotatingBelly implements IRotatingBelly {
//
//
//    private TalonSRX motor;
//    private DigitalInput limitSwitch;
//    public TalonSRXRotatingBelly(){
//
//        motor = new TalonSRX(RobotMap.RotatingBelly.MOTOR_ID);
//        motor.setInverted(true);
//
//        limitSwitch = new DigitalInput(RobotMap.RotatingBelly.MACRO_SWITCH_DIO_PORT);
//    }
//
//
//    @Override
//    public void setPower(double power){
//        motor.set(TalonSRXControlMode.PercentOutput,power);
//    }
//
//    @Override
//    public void stop() {
//        setPower(0);
//    }
//
//
//
//    @Override
//    public void updateInputs(RotatingBellyInputs inputs){
//        inputs.motorVoltage = motor.getMotorOutputVoltage();
//        inputs.motorCurrent = motor.getStatorCurrent();
//
//    }
//
//}
