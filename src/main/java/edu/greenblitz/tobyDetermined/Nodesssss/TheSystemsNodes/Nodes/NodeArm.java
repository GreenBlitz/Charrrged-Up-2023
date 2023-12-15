package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.Nodes;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw.ClawState;
import edu.wpi.first.math.util.Units;

public class NodeArm extends GBNode {

    private final static double TOLERANCE_ANGLE = Units.degreesToRadians(3);
    private final static double TOLERANCE_LENGTH = 0.04;
    private final double anglePos;
    private final double extendPos;

    public NodeArm(double extenderPos, double anglePos) {
        super();
        this.extendPos = extenderPos;
        this.anglePos = anglePos;
    }

    public double getExtendPos() {
        return extendPos;
    }

    public double getAnglePos() {
        return anglePos;
    }

    public boolean isAtLength(double length) {
        return Math.abs(extendPos - length) <= TOLERANCE_LENGTH;
    }

    public boolean isAtAngle(double angle) {
        return Math.abs(anglePos - angle) <= TOLERANCE_ANGLE;
    }

    public boolean isAtNode(double angle, double length) {
        return isAtAngle(angle) && isAtLength(length);
    }

}
