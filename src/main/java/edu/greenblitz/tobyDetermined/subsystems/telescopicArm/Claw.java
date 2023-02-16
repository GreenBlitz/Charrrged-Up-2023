package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.concurrent.CompletionException;

public class Claw extends GBSubsystem {
    private static Claw instance;
    private static final double MOTOR_POWER_GRIP = 0.3;
    private static final double MOTOR_POWER_RELEASE = -0.3;
    private TalonSRX motor;
    private DoubleSolenoid solenoid;
    public ClawState state;

    private Claw(){
        motor = new TalonSRX(RobotMap.telescopicArm.claw.MOTOR_ID);
        solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,RobotMap.telescopicArm.claw.SOLENOID_OPEN_CLAW_ID,RobotMap.telescopicArm.claw.SOLENOID_CLOSED_CLAW_ID);
    }
    /**
     *
     * forward -> cone
     * reverse -> cube
     *
     * */

    public static Claw getInstance() {
        if (instance == null) {
            init();
            SmartDashboard.putBoolean("claw initialized via getInstance", true);
        }
        return instance;
    }

    public static void init(){
        instance = new Claw();
    }

    public void cubeCatchMode (){
        solenoid.set(DoubleSolenoid.Value.kReverse);
        state = ClawState.CUBE_MODE;
    }
    public void coneCatchMode (){
        solenoid.set(DoubleSolenoid.Value.kForward);
        state = ClawState.CONE_MODE;
    }

    public void toggleSolenoid (){
        if(solenoid.get() == DoubleSolenoid.Value.kReverse){
            coneCatchMode();
        }else{
            cubeCatchMode();
        }
    }

    public void motorGrip() {
        motor.set(ControlMode.PercentOutput,MOTOR_POWER_GRIP);
    }

    public void motorEject() {
        motor.set(ControlMode.PercentOutput,MOTOR_POWER_RELEASE);
    }

    public void stopMotor (){
        motor.set(ControlMode.PercentOutput,0);
    }

    public enum ClawState{
        CUBE_MODE,
        CONE_MODE;
    }
}
