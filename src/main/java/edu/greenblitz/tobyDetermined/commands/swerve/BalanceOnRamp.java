package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.PigeonGyro;
import edu.wpi.first.math.controller.PIDController;

public class BalanceOnRamp extends SwerveCommand{


    private PigeonGyro gyro;
    private PIDController balancePIDController;


    public BalanceOnRamp (){
        balancePIDController = new PIDController(0,0,0);

        balancePIDController.setP(RobotMap.Swerve.balancePID.getKp());
        balancePIDController.setI(RobotMap.Swerve.balancePID.getKi());
        balancePIDController.setD( RobotMap.Swerve.balancePID.getKd());
        balancePIDController.setTolerance(RobotMap.Swerve.balancePID.getTolerance());

        gyro = swerve.getPigeonGyro();
    }

    @Override
    public void execute() { //assumes front is perpendicular
        swerve.moveByChassisSpeeds(balancePIDController.calculate(Math.sin(gyro.getPitch())),0,0,0);
    }
}

