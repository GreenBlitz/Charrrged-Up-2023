package edu.greenblitz.tobyDetermined.subsystems.swerve.Modules;

import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.littletonrobotics.junction.Logger;

public class SwerveModule {


    ISwerveModule swerveModule;
    SwerveModuleInputsAutoLogged swerveModuleInputs;

    SwerveChassis.Module module;

    public double targetAngle;
    public double targetVel;

    public SwerveModule(SwerveChassis.Module module) {
        this.module = module;

        this.swerveModule = SwerveModuleFactory.create(module);

        swerveModuleInputs = new SwerveModuleInputsAutoLogged();
        this.periodic();
        
    }



    public void rotateToAngle(double angleInRads) {
        double diff = Math.IEEEremainder(angleInRads - getModuleAngle(), 2 * Math.PI);
        diff -= diff > Math.PI ? 2 * Math.PI : 0;
        angleInRads = getModuleAngle() + diff;

        swerveModule.rotateToAngle(angleInRads);
    }

    public double getModuleAngle() {
        return swerveModuleInputs.angularPositionInRads;
    }

    public double getCurrentVelocity() {
        return swerveModuleInputs.linearVelocity;
    }

    public double getCurrentMeters() {
        return swerveModuleInputs.linearMetersPassed;
    }

    public SwerveModulePosition getCurrentPosition() {
        return new SwerveModulePosition(getCurrentMeters(), new Rotation2d(getModuleAngle()));
    }

    public void resetEncoderToValue(double angleInRads) {
        swerveModule.resetAngle(angleInRads);
    }

    public void resetEncoderToValue() {
        resetEncoderToValue(0);
    }

    public void periodic() {
        swerveModule.updateInputs(swerveModuleInputs);
        Logger.processInputs("DriveTrain/Module" + this.module.toString(), swerveModuleInputs);
    }


    public void resetEncoderByAbsoluteEncoder() {
        resetEncoderToValue(swerveModuleInputs.absoluteEncoderPosition);
    }

    public void setLinSpeed(double speed) {
        swerveModule.setLinearVelocity(speed);
    }

    public void stop() {
        swerveModule.stop();
    }

    public SwerveModuleState getModuleState(){
        return new SwerveModuleState(
                getCurrentVelocity(),
                new Rotation2d(getModuleAngle())
        );
    }

    public boolean isAtAngle(double targetAngleInRads, double tolerance) {
        double currentAngleInRads = getModuleAngle();
        return (currentAngleInRads - targetAngleInRads) %(2*Math.PI) < tolerance
                || (targetAngleInRads - currentAngleInRads) % (2*Math.PI) < tolerance;
    }
    public boolean isAtAngle (double errorInRads){
        return isAtAngle(targetAngle, errorInRads);
    }
    public void setModuleState(SwerveModuleState moduleState){
        setLinSpeed(moduleState.speedMetersPerSecond);
        rotateToAngle(moduleState.angle.getRadians());
    }
    public double getAbsoluteEncoderValue(){
        return swerveModuleInputs.absoluteEncoderPosition;
    }



    public void setRotPowerOnlyForCalibrations(double power){
        swerveModule.setAngularVoltage(power * Battery.getInstance().getCurrentVoltage());
    }
    public void setLinPowerOnlyForCalibrations(double power){
        swerveModule.setLinearVoltage(power * Battery.getInstance().getCurrentVoltage());
    }
    public void setLinIdleModeBrake (){
        swerveModule.setLinearIdleModeBrake(true);
    }
    public void setLinIdleModeCoast (){
        swerveModule.setLinearIdleModeBrake(false);
    }
    public void setRotIdleModeBrake(){
        swerveModule.setAngularIdleModeBrake(true);

    }
    public void setRotIdleModeCoast (){
        swerveModule.setAngularIdleModeBrake(false);
    }
    public boolean isEncoderBroken(){
        return !swerveModuleInputs.isAbsoluteEncoderConnected;
    }
    public double getLinVoltage(){
        return swerveModuleInputs.linearVoltage;
    }

}
