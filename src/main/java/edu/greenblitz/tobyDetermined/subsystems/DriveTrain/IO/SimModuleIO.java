package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.Conversions;
import edu.greenblitz.utils.motors.GBFalcon;

public class SimModuleIO implements IModuleIO{

    private final GBFalcon linearMotor;
    private final GBFalcon angularMotor;

    public SimModuleIO(SwerveChassis.Module module){

        switch (module){

            case FRONT_LEFT:
                linearMotor = new GBFalcon(RobotMap.Swerve.SimModuleFrontLeft.linearMotorID);
                linearMotor.setInverted(RobotMap.Swerve.SimModuleFrontLeft.linInverted);
                linearMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseLinConfObj));

                angularMotor = new GBFalcon(RobotMap.Swerve.SimModuleFrontLeft.angleMotorID);
                angularMotor.setInverted(true);
                angularMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseAngConfObj));
                break;
            case FRONT_RIGHT:
                linearMotor = new GBFalcon(RobotMap.Swerve.SimModuleFrontRight.linearMotorID);
                linearMotor.setInverted(RobotMap.Swerve.SimModuleFrontRight.linInverted);
                linearMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseLinConfObj));


                angularMotor = new GBFalcon(RobotMap.Swerve.SimModuleFrontRight.angleMotorID);
                angularMotor.setInverted(true);
                angularMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseAngConfObj));
                break;
            case BACK_LEFT:
                linearMotor = new GBFalcon(RobotMap.Swerve.SimModuleBackLeft.linearMotorID);
                linearMotor.setInverted(RobotMap.Swerve.SimModuleBackLeft.linInverted);
                linearMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseLinConfObj));


                angularMotor = new GBFalcon(RobotMap.Swerve.SimModuleBackLeft.angleMotorID);
                angularMotor.setInverted(true);
                angularMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseAngConfObj));
                break;
            case BACK_RIGHT:
                linearMotor = new GBFalcon(RobotMap.Swerve.SimModuleBackRight.linearMotorID);
                linearMotor.setInverted(RobotMap.Swerve.SimModuleBackRight.linInverted);
                linearMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseLinConfObj));

                angularMotor = new GBFalcon(RobotMap.Swerve.SimModuleBackRight.angleMotorID);
                angularMotor.setInverted(true);
                angularMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseAngConfObj));
                break;
            default:
                throw new IllegalArgumentException("Invalid module");

        }
    }

    @Override
    public void setLinearVelocity(double speed) {
        linearMotor.getSimCollection().setIntegratedSensorVelocity((int) Conversions.MK4IConversions.convertMetersPerSecondToTicksPer100ms(speed));
    }

    @Override
    public void rotateToAngle(double angleInRadians) {
        angularMotor.getSimCollection().setIntegratedSensorRawPosition((int) Conversions.MK4IConversions.convertRadsToTicks(angleInRadians));
    }

    @Override
    public void stop() {
        linearMotor.getSimCollection().setIntegratedSensorVelocity(0);
        angularMotor.getSimCollection().setIntegratedSensorVelocity(0);
    }

    @Override
    public void updateInputs(IModuleIOInputs inputs) {
        inputs.linearVelocity = Conversions.MK4IConversions.convertTicksToMeters(linearMotor.getSelectedSensorPosition());
        inputs.angularVelocity = Conversions.MK4IConversions.convertTicksToRads(angularMotor.getSelectedSensorPosition());

        inputs.linearVoltage = linearMotor.getMotorOutputVoltage();
        inputs.angularVoltage = angularMotor.getMotorOutputVoltage();

        inputs.linearCurrent = linearMotor.getSupplyCurrent();
        inputs.angularCurrent = angularMotor.getStatorCurrent();

        inputs.linearMetersPassed = Conversions.MK4IConversions.convertTicksToMeters(linearMotor.getSelectedSensorPosition());
        inputs.angularPositionInRads = Conversions.MK4IConversions.convertTicksToRads(angularMotor.getSelectedSensorPosition());

        inputs.absoluteEncoderPosition = angularMotor.getSelectedSensorPosition();
        inputs.isAbsoluteEncoderConnected = true;
    }

    //write a config object for the swerve module

    public static class SimModuleConfigObject {
        private int angleMotorID;
        private int linearMotorID;
        private boolean linInverted;

        public SimModuleConfigObject(int angleMotorID, int linearMotorID, boolean linInverted){
            this.angleMotorID = angleMotorID;
            this.linearMotorID = linearMotorID;
            this.linInverted = linInverted;
        }
    }









}
