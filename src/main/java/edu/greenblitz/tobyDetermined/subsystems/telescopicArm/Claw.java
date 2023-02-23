package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.logger;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Claw extends GBSubsystem {
    private static Claw instance;
    private static final double MOTOR_POWER_GRIP = 0.3;
    private static final double MOTOR_POWER_RELEASE = -0.3;
    private GBSparkMax motor;
    
    private Boolean log_flag = true;
	private DataLog log;
	private StringLogEntry commandloger;

    private Claw(){
        motor = new GBSparkMax(RobotMap.telescopicArm.claw.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        log = logger.getInstance().get_log();
		this.commandloger = new StringLogEntry(this.log, "/Arm/Claw/HighLevel/CommandLogger");

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
        }
        return instance;
    }

    public static void init(){
        instance = new Claw();
    }

    public void grip() {
        motor.set(MOTOR_POWER_GRIP);
        if (log_flag){commandloger.append("Grip");}
    }

    public void eject() {
        motor.set(MOTOR_POWER_RELEASE);
        if (log_flag){commandloger.append("Eject");}
    }

    public void stopMotor (){
        motor.set(ControlMode.PercentOutput,0);
    }

    public enum ClawState{
        CUBE_MODE,
        CUBE_IN,
        CONE_IN,
        CONE_MODE;
    }
}
