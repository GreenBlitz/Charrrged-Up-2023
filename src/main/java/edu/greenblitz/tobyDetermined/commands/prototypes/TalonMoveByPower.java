package edu.greenblitz.tobyDetermined.commands.prototypes;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.motors.GBSparkMax;

public class TalonMoveByPower extends GBCommand {
    private int[] ids;
    private TalonSRX[] motors;
    private double power;

    public TalonMoveByPower(double power, int... ids){
        this.power = power;
        this.ids = ids;
    }

    @Override
    public void initialize() {
        motors = new TalonSRX[ids.length];
        for (int i = 0; i <ids.length; i++){
            motors[i] = new TalonSRX(ids[i]);
        }
    }

    @Override
    public void execute() {
        for (TalonSRX motor: motors){
            motor.set(TalonSRXControlMode.PercentOutput,power);
        }
    }

    @Override
    public void end(boolean interrupted) {
        for (TalonSRX motor: motors){
            motor.set(TalonSRXControlMode.PercentOutput,0);
        }
    }
}
