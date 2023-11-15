package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.CurrentNode;
import edu.greenblitz.tobyDetermined.Nodesssss.MidNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.GBMath;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions;

public class NodeToNeighbourCommand extends GBCommand {
    private final Extender extender;
    private final Elbow elbowSub;

    private PresetPositions start;
    private PresetPositions end;
    private double COMBINED_VELOCITY;
    private static final double STATIC_COMBINED_VELOCITY = 2; // Meters Per Second
    private static final double MAX_EXTENDER_VELOCITY = 1.5; //In Meters Per Second
    private static final double MAX_ANGULAR_VELOCITY = 5;//In Radians Per Second
    
    public NodeToNeighbourCommand(PresetPositions start, PresetPositions end) {
        extender = Extender.getInstance();
        elbowSub = Elbow.getInstance();
        require(elbowSub);
        require(extender);
        COMBINED_VELOCITY = 0;
        this.start = start;
        this.end = end;
        
        
    }
    
    @Override
    public void initialize() {
        double distance;
        double length1 = NodeBase.getNode(start).getExtendPos() + RobotMap.TelescopicArm.Extender.STARTING_LENGTH;
        double length2 = NodeBase.getNode(end).getExtendPos() + RobotMap.TelescopicArm.Extender.STARTING_LENGTH;
        if (extender.getLength()+ NodeBase.getNode(end).getExtendPos() <= 0.1)
            distance = GBMath.getBow(length1,NodeBase.getNode(start).getAnglePos(),length2,NodeBase.getNode(end).getAnglePos());
        else
            distance = GBMath.distance(
                    GBMath.polarToCartesian(length1,NodeBase.getNode(start).getAnglePos()),
                    GBMath.polarToCartesian(length2,NodeBase.getNode(end).getAnglePos())
            );
        COMBINED_VELOCITY = STATIC_COMBINED_VELOCITY * GBMath.sigmoid(distance,1,5.9,0.3);
        SmartDashboard.putNumber("Combined Velocity",COMBINED_VELOCITY);
    }
    
    public static double getRatioBetweenAngleAndLength(double sideA, double sideB, double gamma) {
        double sideC = GBMath.lawOfCosines(sideA, sideB, gamma);
        double height = sideB * Math.sin(gamma);
        double adjacent = Math.sqrt(sideC * sideC - height * height);
        return height / adjacent;
    }

    public double calculateExtenderVelocity(double ratio, NodeArm nodeEndIndex) {
        double signOfExtender = Math.signum(nodeEndIndex.getExtendPos() - extender.getLength());
        double extenderVelocity = Math.sqrt(COMBINED_VELOCITY * COMBINED_VELOCITY / (ratio * ratio + 1));

        SmartDashboard.putNumber("wanted extender vel", extenderVelocity);

        return signOfExtender * extenderVelocity;
    }

    public double calculateAngularVelocity(double startVelocity, NodeArm nodeEndIndex) {

        double signOfAngle = Math.signum(nodeEndIndex.getAnglePos() - elbowSub.getAngleRadians());
        double magnitudeOfVelocity = startVelocity / (extender.getLength() + RobotMap.TelescopicArm.Extender.STARTING_LENGTH);
        
        SmartDashboard.putNumber("wanted angular vel", Math.abs(magnitudeOfVelocity)*signOfAngle);
        
        magnitudeOfVelocity = Math.min(MAX_ANGULAR_VELOCITY, magnitudeOfVelocity);
        magnitudeOfVelocity = Math.max(-MAX_ANGULAR_VELOCITY, magnitudeOfVelocity);

        return signOfAngle * Math.abs(magnitudeOfVelocity);
    }

    public void moveArm(NodeArm nodeEndIndex) {
        double start = extender.getLength() + RobotMap.TelescopicArm.Extender.STARTING_LENGTH;
        double end = nodeEndIndex.getExtendPos() + RobotMap.TelescopicArm.Extender.STARTING_LENGTH;
        double gamma = nodeEndIndex.getAnglePos() - elbowSub.getAngleRadians();
        double ratio = getRatioBetweenAngleAndLength(start, end, gamma);

        double extenderVelocity = calculateExtenderVelocity(ratio, nodeEndIndex);
        double angularVelocity = calculateAngularVelocity(ratio * extenderVelocity, nodeEndIndex);

        extenderVelocity = Math.min(MAX_EXTENDER_VELOCITY, extenderVelocity);
        extenderVelocity = Math.max(-MAX_EXTENDER_VELOCITY, extenderVelocity);

        if (!(nodeEndIndex.getIsAtAngle(elbowSub.getAngleRadians()))) {
            elbowSub.setAngSpeed(angularVelocity, elbowSub.getAngleRadians(), extender.getLength());
            SmartDashboard.putBoolean("isInThreshold", true);
        } else {
            elbowSub.stop();
            SmartDashboard.putBoolean("isInThreshold", false);
        }

        if (end >= 0.1 || start >= 0.1)
            extender.setLinSpeed(extenderVelocity);
        else
            extender.setMotorVoltage(Extender.getStaticFeedForward(elbowSub.getAngleRadians()));
        
    }

    public boolean isInPlace(NodeArm target) {
        return target.getIsAtNode(elbowSub.getAngleRadians(),extender.getLength());
    }

    @Override
    public void execute() {
        moveArm(NodeBase.getNode(end));
    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putBoolean("isInTarget", false);
        if (isInPlace(NodeBase.getNode(end))) {
            SmartDashboard.putBoolean("isInTarget", true);
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            MidNode.getInstance().setNewMidNode(start, end, extender.getLength(), elbowSub.getAngleRadians());
            CurrentNode.setCurrentNode(PresetPositions.MID_NODE);
        } else
            CurrentNode.setCurrentNode(end);

        SmartDashboard.putString("currentNode", CurrentNode.getCurrentNode().toString());
    }
}
