package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Claw extends GBSubsystem {
    private static Claw instance;
    private TalonSRX motor;
    private DoubleSolenoid solenoid;
    public ClawState state;

    private Claw() {
        motor = new TalonSRX(RobotMap.TelescopicArm.Claw.MOTOR_ID);
        solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.TelescopicArm.Claw.SOLENOID_OPEN_CLAW_ID, RobotMap.TelescopicArm.Claw.SOLENOID_CLOSED_CLAW_ID);
    }

    /**
     * forward -> cone
     * reverse -> cube
     */

    public static Claw getInstance() {
        init();
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new Claw();
        }
    }

    public void cubeCatchMode() {
        solenoid.set(DoubleSolenoid.Value.kReverse);
        state = ClawState.CUBE_MODE;
    }

    public void coneCatchMode() {
        solenoid.set(DoubleSolenoid.Value.kForward);
        state = ClawState.CONE_MODE;
    }

    public void toggleSolenoid() {
        if (solenoid.get() == DoubleSolenoid.Value.kReverse) {
            coneCatchMode();
        } else {
            cubeCatchMode();
        }
    }

    public void motorGrip() {
        motor.set(ControlMode.PercentOutput, RobotMap.TelescopicArm.Claw.MOTOR_POWER_GRIP);
    }

    public void motorEject() {
        motor.set(ControlMode.PercentOutput, RobotMap.TelescopicArm.Claw.MOTOR_POWER_RELEASE);
    }

    public void stopMotor() {
        motor.set(ControlMode.PercentOutput, 0);
    }

    public enum ClawState{
        CUBE_MODE,
        CUBE_IN,
        CONE_IN,
        CONE_MODE;
    }
}
