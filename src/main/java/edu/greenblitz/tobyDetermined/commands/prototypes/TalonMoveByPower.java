package edu.greenblitz.tobyDetermined.commands.prototypes;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.Robot;
import edu.greenblitz.tobyDetermined.subsystems.Prototypes;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.motors.GBSparkMax;

public class TalonMoveByPower extends GBCommand {
    private int[] ids;
    private double power;

    public TalonMoveByPower(double power, int... ids) {
        this.power = power;
        this.ids = ids;
    }

    @Override
    public void initialize() {
        for (int id:ids) {
            if(!Prototypes.talons.containsKey(id)){
                Prototypes.talons.put(id,new TalonSRX(id));
            }
        }
    }

    @Override
    public void execute() {
        for (int id: ids) {
            Prototypes.talons.get(id).set(TalonSRXControlMode.PercentOutput,power);
        }
    }

    @Override
    public void end(boolean interrupted) {
        for (int id: ids) {
            Prototypes.talons.get(id).set(TalonSRXControlMode.PercentOutput,0);
        }
    }
}
