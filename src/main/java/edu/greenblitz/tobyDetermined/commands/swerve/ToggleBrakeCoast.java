package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ToggleBrakeCoast extends SwerveCommand {

    private static boolean isBrake = true;

    public ToggleBrakeCoast (){
        isBrake = true;
        setLinToBrake();

    }

    @Override
    public void initialize() {
        if (isBrake){
            setLinToCoast();
        }else{
            setLinToBrake();
        }
        isBrake = !isBrake;
        SmartDashboard.putBoolean("isBrake",isBrake);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    /** set all linear motors to brake mode */
    public void setLinToBrake (){
        swerve.setIdleModeBrake();
    }

    /** set all linear motors to coast mode */

    public void setLinToCoast (){
        swerve.setIdleModeCoast();
    }

}
