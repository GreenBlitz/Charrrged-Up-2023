package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.ModuleIOs;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;

public class SwerveModuleFactory {


    public static ISwerveModule create(SwerveChassis.Module module) {

        switch (RobotMap.ROBOT_TYPE) {

            case FRANKENSTEIN:
                return new MK4ISwerveModule(module);
            case SIMULATION:
                return new SimulationSwerveModule(module);
            case PEGA_SWERVE:
            case REPLAY:
            default:
                return new ISwerveModule() {
                };
        }
    }
}
