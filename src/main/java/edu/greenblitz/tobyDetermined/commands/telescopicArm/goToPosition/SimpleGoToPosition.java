package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngleRadians;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class SimpleGoToPosition extends GBCommand {
    private double lengthInMeters;
    private double angleInRads;

    protected SimpleGoToPosition(double lengthInMeters, double angleInRads) {
        require(Extender.getInstance());
        require(Elbow.getInstance());
        this.lengthInMeters = lengthInMeters;
        this.angleInRads = angleInRads;
    }

    @Override
    public void execute() {
        Extender.getInstance().moveTowardsLength(lengthInMeters);
        Elbow.getInstance().moveTowardsAngleRadians(angleInRads);
    }

    @Override
    public boolean isFinished() {
        boolean isExtenderReady = Extender.getInstance().isAtLength(lengthInMeters) && Extender.getInstance().isNotMoving();
        boolean isElbowReady = Elbow.getInstance().isAtAngle(angleInRads) && Elbow.getInstance().isNotMoving();
        return isElbowReady && isExtenderReady;
    }
}
