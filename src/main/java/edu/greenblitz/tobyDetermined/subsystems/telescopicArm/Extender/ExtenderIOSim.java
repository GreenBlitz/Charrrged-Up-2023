package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

public class ExtenderIOSim implements ExtenderIO {
    ElevatorSim extenderSim;
    private double appliedVoltage;

    public ExtenderIOSim() {
        this.extenderSim = new ElevatorSim(
                DCMotor.getNEO(1),
                1 / RobotMap.TelescopicArm.Extender.GEAR_RATIO,
                6,
                0.0165,
                RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT,
                RobotMap.TelescopicArm.Extender.EXTENDED_LENGTH,
                false
        );
    }

    @Override
    public void setPower(double power) {
        setVoltage(power * 12);
    }

    @Override
    public void setVoltage(double voltage) {
        appliedVoltage = MathUtil.clamp(voltage, -12, 12);
        extenderSim.setInputVoltage(appliedVoltage);
    }

    @Override
    public void updateInputs(ExtenderInputs inputs) {
        extenderSim.update(RobotMap.SimulationConstants.TIME_STEP);

        inputs.appliedOutput = appliedVoltage;
        inputs.outputCurrent = extenderSim.getCurrentDrawAmps();
        inputs.position = extenderSim.getPositionMeters();
        inputs.velocity = extenderSim.getVelocityMetersPerSecond();
        inputs.reverseLimitSwitchPressed = extenderSim.hasHitLowerLimit();
        inputs.kP = 1;
        inputs.kI = 0;
        inputs.kD = 0;
    }

}
