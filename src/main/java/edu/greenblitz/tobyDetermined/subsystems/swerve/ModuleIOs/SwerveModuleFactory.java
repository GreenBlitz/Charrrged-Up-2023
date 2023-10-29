package edu.greenblitz.tobyDetermined.subsystems.swerve.ModuleIOs;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;

public class SwerveModuleFactory {


    public static ISwerveModule create(SwerveChassis.Module module) {

        switch (RobotMap.ROBOT_TYPE) {
            case REPLAY:
                return new ReplaySwerveModule();
            case FRANKENSTEIN:
                return new MK4ISwerveModule(module);
            case PEGA_SWERVE:
            case SIMULATION:
            default:
                return new SimulationSwerveModule(module);

        }
    }
}
