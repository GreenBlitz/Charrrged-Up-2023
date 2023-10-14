package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.IO.RotatingBellyIO;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.IO.RotatingBellyIOTalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotatingBelly extends GBSubsystem {


    private static RotatingBelly instance;

    private RotatingBellyIO io;
    private final RotatingBellyIO.RotatingBellyInputs inputs = new RotatingBellyIO.RotatingBellyInputs();

//    private BellyGameObjectSensor colorSensor;

    private RotatingBelly (){
        io = new RotatingBellyIOTalonSRX();
    }
    public void setPower(double power){
        io.setPower(power);
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

    public void stop (){
        io.stop();
    }
    public boolean isLimitSwitchPressed(){
        return io.isLimitSwitchPressed();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("is",io.isLimitSwitchPressed());
    }
}
