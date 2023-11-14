package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import org.littletonrobotics.junction.Logger;

import java.util.function.DoubleSupplier;

import static edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement.*;

public class RotateAroundAxis extends SwerveCommand {

    Translation2d newAxis;
    SwerveDriveKinematics kinematics;
    boolean isSlow;

    public RotateAroundAxis(boolean isSlow , Translation2d newAxis, SwerveDriveKinematics kinematics) {
        this.isSlow = isSlow;
        this.newAxis = newAxis;
        this.kinematics = kinematics;
    }

    @Override
    public void initialize() {
        ANG_SPEED_FACTOR = 5;
        LIN_SPEED_FACTOR = RobotMap.Swerve.MAX_VELOCITY;
        if (isSlow) {
            ANG_SPEED_FACTOR = SLOW_ANG_SPEED_FACTOR;
            LIN_SPEED_FACTOR = SLOW_LIN_SPEED_FACTOR;
        }
    }

    @Override
    public void execute() {

        SwerveChassis.getInstance().setModuleStates(
                kinematics.toSwerveModuleStates(
                        new ChassisSpeeds(
                                -OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_X) * LIN_SPEED_FACTOR,
                                OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_Y) * LIN_SPEED_FACTOR,
                                -OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X) * ANG_SPEED_FACTOR
                        ),
                        newAxis
                )
        );
    }
}
