package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ProxyCommand;

public class NodeFullPathCommand extends ProxyCommand {
    private final Extender extender;
    private final Elbow elbow;

    public NodeFullPathCommand(RobotMap.TelescopicArm.PresetPositions endNode) {
        super(new NodeToNeighbourSupplier(endNode));
        extender = Extender.getInstance();
        elbow = Elbow.getInstance();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        stopMotors();
    }

    public void stopMotors() {
        extender.stop();
        elbow.stop();
    }
}

