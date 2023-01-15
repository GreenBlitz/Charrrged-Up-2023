package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.PigeonGyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BalanceOnRamp extends SwerveCommand{


    private PigeonGyro gyro;
    private PIDController balancePIDController;


    public BalanceOnRamp (){
        balancePIDController = RobotMap.Swerve.balancePID.getPIDController();
        gyro = swerve.getPigeonGyro();
        balancePIDController.setSetpoint(0);
    }

    @Override
    public void execute() { //assumes front is perpendicular
        SmartDashboard.putBoolean("atSetPoint",balancePIDController.atSetpoint());
        double velocity =balancePIDController.calculate(Math.sin(gyro.getRoll()));
        if (!balancePIDController.atSetpoint()) {
        swerve.moveByChassisSpeeds(velocity, 0, 0, 0);
        } else {
            swerve.stop();
        }
//        SmartDashboard.putNumber("bal pid",balancePIDController.calculate(Math.sin(gyro.getRoll())));
//        SmartDashboard.putNumber("pos err",balancePIDController.getPositionError());
//        SmartDashboard.putNumber("vel err",balancePIDController.getVelocityError());
    }
}

