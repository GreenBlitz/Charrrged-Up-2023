package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystem.CurrentNodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.MidNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ArmSimulation;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.GBMath;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.littletonrobotics.junction.Logger;

import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos;

public class NodeToNeighbourCommand extends GBCommand {
    private final Extender extender;
    private final Elbow elbow;
    private NodeArm startNodeArm;
    private NodeArm endNodeArm;
    private SystemsPos start;
    private SystemsPos end;
    private double COMBINED_VELOCITY;
    private static final double STATIC_COMBINED_VELOCITY = 2; // Meters Per Second
    private static final double MAX_EXTENDER_VELOCITY = 1.5; //In Meters Per Second
    private static final double MAX_ANGULAR_VELOCITY = 5;//In Radians Per Second

    private static final double STARTING_LENGTH = RobotMap.TelescopicArm.Extender.STARTING_LENGTH;
    private static final double EXTENDER_STOPPING_THRESHOLD = 0.1 + STARTING_LENGTH;

    public NodeToNeighbourCommand(SystemsPos start, SystemsPos end) {
        extender = Extender.getInstance();
        elbow = Elbow.getInstance();
        require(elbow);
        require(extender);
        COMBINED_VELOCITY = 0;
        this.start = start;
        this.end = end;
        startNodeArm = (NodeArm)NodeBase.getNode(start);
        endNodeArm = (NodeArm)NodeBase.getNode(end);


    }

    @Override
    public void initialize() {
        double distance;
        double length1 = startNodeArm.getExtendPos() + STARTING_LENGTH;
        double length2 = endNodeArm.getExtendPos() + STARTING_LENGTH;
        if (extender.getLength()+ endNodeArm.getExtendPos() <= 0.1)
            distance = GBMath.getBow(length1,startNodeArm.getAnglePos(),length2,endNodeArm.getAnglePos());
        else
            distance = GBMath.distance(
                    GBMath.polarToCartesian(length1,startNodeArm.getAnglePos()),
                    GBMath.polarToCartesian(length2,endNodeArm.getAnglePos())
            );
        COMBINED_VELOCITY = STATIC_COMBINED_VELOCITY * GBMath.sigmoid(distance,1,5.9,0.3);
        SmartDashboard.putNumber("Combined Velocity",COMBINED_VELOCITY);

        Logger.getInstance().recordOutput("Arm/TargetPose3D/endNode",ArmSimulation.getArmPosition(endNodeArm.getExtendPos(),(startNodeArm.getAnglePos())));
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

        SmartDashboard.putNumber("wanted extender vel", extenderVelocity * signOfExtender);

        return signOfExtender * extenderVelocity;
    }

    public double calculateAngularVelocity(double startVelocity, NodeArm nodeEndIndex) {

        double signOfAngle = Math.signum(nodeEndIndex.getAnglePos() - elbow.getAngleRadians());
        double magnitudeOfVelocity = startVelocity / (extender.getLength() + STARTING_LENGTH);

        SmartDashboard.putNumber("wanted angular vel", Math.abs(magnitudeOfVelocity)*signOfAngle);

        magnitudeOfVelocity = Math.min(MAX_ANGULAR_VELOCITY, magnitudeOfVelocity);
        magnitudeOfVelocity = Math.max(-MAX_ANGULAR_VELOCITY, magnitudeOfVelocity);

        return signOfAngle * Math.abs(magnitudeOfVelocity);
    }

    public void moveArm(NodeArm nodeEndIndex) {
        double start = extender.getLength() + STARTING_LENGTH;
        double end = nodeEndIndex.getExtendPos() + STARTING_LENGTH;
        double gamma = nodeEndIndex.getAnglePos() - elbow.getAngleRadians();
        double ratio = getRatioBetweenAngleAndLength(start, end, gamma);

        double extenderVelocity = calculateExtenderVelocity(ratio, nodeEndIndex);
        double angularVelocity = calculateAngularVelocity(ratio * extenderVelocity, nodeEndIndex);

        extenderVelocity = Math.min(MAX_EXTENDER_VELOCITY, extenderVelocity);
        extenderVelocity = Math.max(-MAX_EXTENDER_VELOCITY, extenderVelocity);

        if (!(nodeEndIndex.getIsAtAngle(elbow.getAngleRadians()))) {
            elbow.setMotorVoltage(Elbow.getDynamicFeedForward(angularVelocity,extender.getLength(), elbow.getAngleRadians()));
            SmartDashboard.putBoolean("isInThreshold", true);
        } else {
            elbow.stop();
            SmartDashboard.putBoolean("isInThreshold", false);
        }

        if (end >= EXTENDER_STOPPING_THRESHOLD || start >= EXTENDER_STOPPING_THRESHOLD)
            extender.setMotorVoltage(Extender.getDynamicFeedForward(extenderVelocity, Extender.getDynamicFeedForward(extenderVelocity,Elbow.getInstance().getAngleRadians())));
        else
            extender.setMotorVoltage(Extender.getStaticFeedForward(elbow.getAngleRadians()));

    }

    public boolean isInPlace(NodeArm target) {
        return target.getIsAtNode(elbow.getAngleRadians(),extender.getLength());
    }

    @Override
    public void execute() {
        moveArm(endNodeArm);
    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putBoolean("isInTarget",false);
        if (isInPlace(endNodeArm)) {
            SmartDashboard.putBoolean("isInTarget", true);
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            MidNode.getInstance().setNewMidNode(start, end);
            CurrentNodeArm.setCurrentNode(SystemsPos.MID_NODE);
        } else
            CurrentNodeArm.setCurrentNode(end);

        SmartDashboard.putString("currentNode", CurrentNodeArm.getCurrentNode().toString());
    }
}
