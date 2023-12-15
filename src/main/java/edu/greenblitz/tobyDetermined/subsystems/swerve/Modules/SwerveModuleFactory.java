package edu.greenblitz.tobyDetermined.subsystems.swerve.Modules;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;

public class SwerveModuleFactory {


    public static ISwerveModule create(SwerveChassis.Module module) {

        switch (RobotMap.ROBOT_TYPE) {
            case REPLAY:
                return new ReplaySwerveModule();
            case FRANKENSTEIN:
               // return new MK4ISwerveModule(module);
            case PEGA_SWERVE:
                return new KazaSwerveModule(module);
            case SIMULATION:
            default:
                return new SimulationSwerveModule(module);

        }
    }
}
