package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

public class ExtenderIOSim implements ExtenderIO {

    ElevatorSim extenderSim;
    public ExtenderIOSim (){
        this.extenderSim = new ElevatorSim(
                DCMotor.getNEO(1),
                1 / RobotMap.TelescopicArm.Extender.GEAR_RATIO,
                6,
                0.0165,
                RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT,
                RobotMap.TelescopicArm.Extender.EXTENDED_LENGTH,
                true
        );
    }










}
