package edu.greenblitz.tobyDetermined.commands.prototypes;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.Robot;
import edu.greenblitz.tobyDetermined.subsystems.Prototypes;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.motors.GBSparkMax;

public class NeoMoveByPower extends GBCommand {
    private int[] ids;
    private double power;

    public NeoMoveByPower(double power, int... ids) {
        this.power = power;
        this.ids = ids;
    }

    @Override
    public void initialize() {
        for (int id:ids) {
            if(!Prototypes.sparks.containsKey(id)){
                Prototypes.sparks.put(id,new GBSparkMax(id, CANSparkMaxLowLevel.MotorType.kBrushless));
            }
        }
    }

    @Override
    public void execute() {
        for (int id: ids) {
            Prototypes.sparks.get(id).set(power);
        }
    }

    @Override
    public void end(boolean interrupted) {
        for (int id: ids) {
            Prototypes.sparks.get(id).set(0);
        }

    }
}
