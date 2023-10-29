package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;

public class SimulationGyro implements IGyroIO {

    private GyroIOInputsAutoLogged lastInputs = new GyroIOInputsAutoLogged();
    @Override
    public void updateInputs(GyroIOInputsAutoLogged inputs) {
        inputs.roll = 0;
        inputs.pitch = 0;
        inputs.yaw += SwerveChassis.getInstance().getChassisSpeeds().omegaRadiansPerSecond * RobotMap.SimulationConstants.TIME_STEP;

        lastInputs = inputs;
    }

    @Override
    public void setYaw(double yaw) {
        lastInputs.yaw = yaw;
    }

    @Override
    public void setPitch(double pitch) {
        lastInputs.pitch = pitch;
    }

    @Override
    public void setRoll(double roll) {
        lastInputs.roll = roll;
    }
}
