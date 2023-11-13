package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.littletonrobotics.junction.Logger;

import static edu.greenblitz.tobyDetermined.RobotMap.Pneumatics.PneumaticsController.ID;

public class Claw extends GBSubsystem {
    private static Claw instance;
    public ClawState state;
    private IClaw claw;
    private ClawInputsAutoLogged inputs = new ClawInputsAutoLogged();

    private Claw() {
        claw = ClawFactory.create();

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
        state = inputs.isOpen ? ClawState.CONE_MODE : ClawState.CUBE_MODE;
        claw.updateInputs(inputs);
        Logger.getInstance().processInputs("Claw", inputs);
    }

    public void cubeCatchMode() {
        claw.setSolenoidState(DoubleSolenoid.Value.kReverse);
        state = ClawState.CUBE_MODE;
    }

    public void coneCatchMode() {
        claw.setSolenoidState(DoubleSolenoid.Value.kForward);
        state = ClawState.CONE_MODE;
    }

    public void toggleSolenoid() {
        if (inputs.isOpen) {
            coneCatchMode();
        } else {
            cubeCatchMode();
        }
    }

    public void motorGrip() {
        claw.setPower( RobotMap.TelescopicArm.Claw.MOTOR_POWER_CONE);
    }

    public void motorGrip(double power){
        claw.setPower(power);
    }

    public void motorEject() {
        claw.setPower(RobotMap.TelescopicArm.Claw.MOTOR_POWER_RELEASE);
    }

    public void stopMotor() {
        claw.setPower(0);
    }

    public enum ClawState{
        CUBE_MODE,
        CONE_MODE
    }
}
