package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.greenblitz.utils.GBCommand;

public class DriveSideWays extends SwerveCommand {


    private static final double SLOW_LIN_SPEED_FACTOR = 0.4;
    private double speed;

    public enum Direction {
        RIGHT,
        LEFT
    }

    public DriveSideWays(DriveSideWays.Direction direction, double speed) {
        switch (direction) {
            case LEFT:
                this.speed = speed * -1;
                break;
            case RIGHT:
                this.speed = speed;
                break;
        }
    }

    @Override
    public void execute() {
        swerve.moveByChassisSpeeds(
                0,
                speed * SLOW_LIN_SPEED_FACTOR,
                0,
                swerve.getChassisAngle());

    }

    @Override
    public void end(boolean interrupted) {
        swerve.stop();
    }


}
