package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.hid.SmartJoystick;

public class DriveRightwardOnly extends SwerveCommand {

    public DriveRightwardOnly(){
    }

    @Override
    public void execute() {

        //needs to add an if to make sure that the chassis won't fall of the ramp

        swerve.moveByChassisSpeeds(
                0,
                OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_X),
                OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X),
                swerve.getChassisAngle());

    }

//    @Override
//    public boolean isFinished() {
//        //Chassis about to fall from ramp
//    }


    @Override
    public void end(boolean interrupted) {
        swerve.stop();
    }
}
