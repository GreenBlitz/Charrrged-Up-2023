package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.TheNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;
import edu.wpi.first.math.util.Units;

public class NodeArm extends GBNode {

    private final static double TOLERANCE_ANGLE = Units.degreesToRadians(3);
    private final static double TOLERANCE_LENGTH = 0.04;
    private final double anglePosition;
    private final double extendPosition;

    public NodeArm(double extendPosition, double anglePosition) {
        super();
        this.extendPosition = extendPosition;
        this.anglePosition = anglePosition;
    }

    public double getExtendPosition() {
        return extendPosition;
    }

    public double getAnglePosition() {
        return anglePosition;
    }

    public boolean isAtLength(double length) {
        return Math.abs(extendPosition - length) <= TOLERANCE_LENGTH;
    }

    public boolean isAtAngle(double angle) {
        return Math.abs(anglePosition - angle) <= TOLERANCE_ANGLE;
    }

    public boolean isAtNode(double angle, double length) {
        return isAtAngle(angle) && isAtLength(length);
    }

}
