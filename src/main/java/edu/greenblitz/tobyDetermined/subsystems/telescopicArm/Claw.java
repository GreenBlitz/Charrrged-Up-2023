package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import static edu.greenblitz.tobyDetermined.RobotMap.Pneumatics.PneumaticsController.ID;

public class Claw extends GBSubsystem {
    private static Claw instance;
    private GBSparkMax motor;
    private DoubleSolenoid solenoid;
    public ClawState state;
    public boolean isUsingSubsystem;

    private Claw() {
        motor = new GBSparkMax(RobotMap.TelescopicArm.Claw.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.config(RobotMap.TelescopicArm.Claw.CLAW_CONFIG_OBJECT);
        solenoid = new DoubleSolenoid(ID, RobotMap.Pneumatics.PneumaticsController.PCM_TYPE, RobotMap.TelescopicArm.Claw.SOLENOID_OPEN_CLAW_ID, RobotMap.TelescopicArm.Claw.SOLENOID_CLOSED_CLAW_ID);
        isUsingSubsystem = false;
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

    @Override
    public void periodic() {
        state = solenoid.get() == DoubleSolenoid.Value.kForward ? ClawState.CONE_MODE : ClawState.CUBE_MODE;
        if (ObjectSelector.IsCube() && !isUsingSubsystem) {
            motorGrip(0.1);
        }
        if (ObjectSelector.IsCone() && !isUsingSubsystem){
            stopMotor();

        }
        SmartDashboard.putNumber("claw power", motor.get());
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

    public enum ClawState{
        CUBE_MODE,
        CONE_MODE,
        RELEASE,
        CATCH;
    }
}
