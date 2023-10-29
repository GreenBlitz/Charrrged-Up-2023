package edu.greenblitz.tobyDetermined.subsystems.swerve.ModuleIOs;

import edu.greenblitz.tobyDetermined.subsystems.swerve.inputs.SwerveModuleInputsAutoLogged;

public interface ISwerveModule {

    void setLinearVelocity(double speed);


    void rotateToAngle(double angleInRadians);
    void setLinearVoltage(double voltage);
     void setAngularVoltage(double voltage);


     void setLinearIdleModeBrake(boolean isBrake);


     void setAngularIdleModeBrake(boolean isBrake);


     void resetAngle(double angleInRads);



     void stop();

     void updateInputs(SwerveModuleInputsAutoLogged inputs);





}
