package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SdsSwerveModule;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.littletonrobotics.junction.Logger;

public class SwerveModule {


    IModuleIO io;
    IModuleIOInputsAutoLogged inputs;

    SwerveChassis.Module module;

    public double targetAngle;
    public double targetVel;

    public SwerveModule(SwerveChassis.Module module) {
        this.module = module;

        switch (RobotMap.ROBOT_TYPE){

            case Frankenstein:
                io = new MK4iModuleIO(module);
                break;
            case SIMULATION:
                io = new SimModuleIO(module);
                break;
            case pegaSwerve:
            case REPLAY:
            default:
                io = new IModuleIO() {};
        }

        inputs = new IModuleIOInputsAutoLogged();
    }



    public void rotateToAngle(double angleInRads) {
        double diff = Math.IEEEremainder(angleInRads - getModuleAngle(), 2 * Math.PI);
        diff -= diff > Math.PI ? 2 * Math.PI : 0;
        angleInRads = getModuleAngle() + diff;

        io.rotateToAngle(angleInRads);
    }

    public double getModuleAngle() {
        return inputs.angularPositionInRads;
    }

    public double getCurrentVelocity() {
        return inputs.linearVelocity;
    }

    public double getCurrentMeters() {
        return inputs.linearMetersPassed;
    }

    public SwerveModulePosition getCurrentPosition() {
        return new SwerveModulePosition(getCurrentMeters(), new Rotation2d(getModuleAngle()));
    }

    public void resetEncoderToValue(double angleInRads) {
        io.resetAngle(angleInRads);
    }

    public void resetEncoderToValue() {
        resetEncoderToValue(0);
    }

    public void periodic() {
        io.updateInputs(inputs);
        Logger.getInstance().processInputs("Drive/Module" + this.module.toString(), inputs);
    }


    public void resetEncoderByAbsoluteEncoder() {
        resetEncoderToValue(inputs.absoluteEncoderPosition);
    }

    public void setLinSpeed(double speed) {
        io.setLinearVelocity(speed);
    }

    public void stop() {
        io.stop();
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
        return inputs.absoluteEncoderPosition;
    }



    public void setRotPowerOnlyForCalibrations(double power){
        io.setAngularVoltage(power * Battery.getInstance().getCurrentVoltage());
    }
    public void setLinPowerOnlyForCalibrations(double power){
        io.setLinearVoltage(power * Battery.getInstance().getCurrentVoltage());
    }
    public void setLinIdleModeBrake (){
        io.setLinearIdleModeBrake(true);
    }
    public void setLinIdleModeCoast (){
        io.setLinearIdleModeBrake(false);
    }
    public void setRotIdleModeBrake(){
        io.setAngularIdleModeBrake(true);

    }
    public void setRotIdleModeCoast (){
        io.setAngularIdleModeBrake(false);
    }
    public boolean isEncoderBroken(){
        return !inputs.isAbsoluteEncoderConnected;
    }
    public double getLinVoltage(){
        return inputs.linearVoltage;
    }

}
