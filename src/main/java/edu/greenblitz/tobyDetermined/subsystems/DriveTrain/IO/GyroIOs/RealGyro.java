package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.Gyros.NavX;
import edu.greenblitz.utils.Gyros.PigeonGyro;

public class RealGyro implements GyroIO{

    PigeonGyro gyro;
    NavX navX;
    public RealGyro () {
        gyro = new PigeonGyro(RobotMap.gyro.pigeonID);
        navX = new NavX();
    }

    @Override
    public void setYaw(double yaw) {
        gyro.setYawAngle(yaw);
    }

    @Override
    public void setPitch(double pitch) {
        gyro.setPitchAngle(pitch);
    }

    @Override
    public void setRoll(double roll) {
        gyro.setRollAngle(roll);
    }

    @Override
    public void updateInputs(GyroIOInputsAutoLogged inputs) {
        inputs.yaw = gyro.getYaw();
        inputs.pitch = gyro.getPitch();
        inputs.roll = gyro.getRoll();
    }
}
