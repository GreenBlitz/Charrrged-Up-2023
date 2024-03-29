package edu.greenblitz.tobyDetermined.subsystems.swerve.Modules;

import edu.greenblitz.tobyDetermined.subsystems.swerve.Modules.SwerveModuleInputsAutoLogged;

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
