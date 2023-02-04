package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.AngPIDSupplier;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToPos;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.wpilibj.DriverStation;

public class MoveToGrid extends MoveToPos {
    public MoveToGrid(){
        super(Grid.Location.POS0.getPose(DriverStation.Alliance.Blue));
    }
    @Override
    public void initialize() {
        super.initialize();
        pos = Grid.getInstance().getPose().getPose(DriverStation.getAlliance());
    }
}
