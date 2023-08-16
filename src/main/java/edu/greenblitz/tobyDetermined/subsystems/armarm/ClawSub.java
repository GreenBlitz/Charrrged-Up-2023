package edu.greenblitz.tobyDetermined.subsystems.armarm;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import static edu.greenblitz.tobyDetermined.RobotMap.Pneumatics.PneumaticsController.ID;

public class ClawSub extends GBSubsystem {
    private static ClawSub instance;
    private GBSparkMax motor;
    private DoubleSolenoid solenoid;

    public ClawSub.ClawSubState state;

    private ClawSub() {
        motor = new GBSparkMax(RobotMap.TelescopicArm.Claw.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.config(RobotMap.TelescopicArm.Claw.CLAW_CONFIG_OBJECT);
        solenoid = new DoubleSolenoid(ID, RobotMap.Pneumatics.PneumaticsController.PCM_TYPE, RobotMap.TelescopicArm.Claw.SOLENOID_OPEN_CLAW_ID, RobotMap.TelescopicArm.Claw.SOLENOID_CLOSED_CLAW_ID);
    }

    /**
     * forward -> cone
     * reverse -> cube
     */

    public static ClawSub getInstance() {
        init();
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new ClawSub();
        }
    }

    @Override
    public void periodic() {
        state = solenoid.get() == DoubleSolenoid.Value.kForward ? ClawSubState.CONE_MODE : ClawSubState.CUBE_MODE;
    }

    public void cubeCatchMode() {
        solenoid.set(DoubleSolenoid.Value.kReverse);
        state = ClawSubState.CUBE_MODE;
    }

    public void coneCatchMode() {
        solenoid.set(DoubleSolenoid.Value.kForward);
        state = ClawSubState.CONE_MODE;
    }

    public void toggleSolenoid() {
        if (solenoid.get() == DoubleSolenoid.Value.kReverse) {
            coneCatchMode();
        } else {
            cubeCatchMode();
        }
    }

    public void motorGrip() {
        motor.set( RobotMap.TelescopicArm.Claw.MOTOR_POWER_CONE);
    }

    public void motorGrip(double power){
        motor.set(power);
    }

    public void motorEject() {
        motor.set(RobotMap.TelescopicArm.Claw.MOTOR_POWER_RELEASE);
    }

    public void stopMotor() {
        motor.set(0);
    }

    public enum ClawSubState{
        CUBE_MODE,
        CONE_MODE
    }
}
