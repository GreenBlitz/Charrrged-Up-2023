package edu.greenblitz.tobyDetermined.commands.prototypes;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.motors.GBSparkMax;

public class NeoMoveByPower extends GBCommand {
    private int[] ids;
    private GBSparkMax[] motors;
    private double power;

    public NeoMoveByPower(double power, int... ids) {
        this.power = power;
        this.ids = ids;
    }

    @Override
    public void initialize() {
            motors = new GBSparkMax[ids.length];
            for (int i = 0; i < ids.length; i++) {
                motors[i] = new GBSparkMax(ids[i], CANSparkMaxLowLevel.MotorType.kBrushless);
        }
    }

    @Override
    public void execute() {
        for (GBSparkMax motor : motors) {
            motor.set(power);
        }
    }

    @Override
    public void end(boolean interrupted) {
        for (GBSparkMax motor : motors) {
            motor.set(0);
            motor = null;
        }

    }
}
