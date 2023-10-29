package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.ModuleIOs;

import edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.inputs.SwerveModuleInputsAutoLogged;
import org.littletonrobotics.junction.AutoLog;

public interface ISwerveModule {

    default void setLinearVelocity(double speed) {
    }

    default void rotateToAngle(double angleInRadians) {
    }

    default void setLinearVoltage(double voltage) {
    }

    default void setAngularVoltage(double voltage) {
    }

    default void setLinearIdleModeBrake(boolean isBrake) {
    }

    default void setAngularIdleModeBrake(boolean isBrake) {
    }

    default void resetAngle(double angleInRads) {
    }


    default void stop() {
    }

    @AutoLog
    class ModuleIOInputs {


    }

    default void updateInputs(SwerveModuleInputsAutoLogged inputs) {
    }




}
