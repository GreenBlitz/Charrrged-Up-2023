package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.GBCommand;

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
    }

    /** set all linear motors to brake mode */
    public void setLinToBrake (){
        swerve.setIdleModeBrake(SwerveChassis.Module.FRONT_LEFT);
        swerve.setIdleModeBrake(SwerveChassis.Module.FRONT_RIGHT);
        swerve.setIdleModeBrake(SwerveChassis.Module.BACK_LEFT);
        swerve.setIdleModeBrake(SwerveChassis.Module.BACK_RIGHT);
    }

    /** set all linear motors to coast mode */

    public void setLinToCoast (){
        swerve.setIdleModeCoast(SwerveChassis.Module.FRONT_LEFT);
        swerve.setIdleModeCoast(SwerveChassis.Module.FRONT_RIGHT);
        swerve.setIdleModeCoast(SwerveChassis.Module.BACK_LEFT);
        swerve.setIdleModeCoast(SwerveChassis.Module.BACK_RIGHT);
    }

}
