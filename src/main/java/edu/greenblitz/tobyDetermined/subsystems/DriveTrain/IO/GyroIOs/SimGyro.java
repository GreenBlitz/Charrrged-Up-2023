package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;

public class SimGyro implements GyroIO{

    @Override
    public void updateInputs(GyroIOInputsAutoLogged inputs) {
        inputs.roll = 0;
        inputs.pitch = 0;
        inputs.yaw += SwerveChassis.getInstance().getChassisSpeeds().omegaRadiansPerSecond * RobotMap.SimulationConstants.TIME_STEP;
    }
}
