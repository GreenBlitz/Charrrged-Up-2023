package edu.greenblitz.tobyDetermined.commands.telescopicArm.moveByNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.TheNodes.NodeArm;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ArmSimulation;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.greenblitz.utils.GBMath;
import org.littletonrobotics.junction.Logger;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.CreateCurrents.system1CurrentNode;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.CreateCurrents.system1MidNode;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.getNode;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Extender.*;
import static edu.greenblitz.utils.GBMath.limit;


public class StraightLineMotion extends GBCommand {
    private final Extender extender;
    private final Elbow elbow;
    private final SystemsState startState;
    private final SystemsState endState;
    private final NodeArm startingNode;
    private final NodeArm endNode;
    private double combinedVelocity;
    private static final double STATIC_COMBINED_VELOCITY = 1; //Meters Per Second
    private static final double MAX_EXTENDER_VELOCITY = 1.5; //Meters Per Second
    private static final double MAX_ANGULAR_VELOCITY = 3;//Radians Per Second
    private static final double TO_BELLY_VELOCITY = 0.2;
    private static final double ONLY_ELBOW_VELOCITY = 1;

    public StraightLineMotion(SystemsState start, SystemsState end) {
        extender = Extender.getInstance();
        elbow = Elbow.getInstance();
        require(elbow);
        require(extender);
        combinedVelocity = 0;
        this.startState = start;
        this.endState = end;
        startingNode = (NodeArm) getNode(start);
        endNode = (NodeArm) getNode(end);
    }

    @Override
    public void initialize() {
        if (extender.getLength() + endNode.getExtendPosition() <= STRAIGHT_LINE_UNAVAILABLE_LENGTH)
            combinedVelocity = ONLY_ELBOW_VELOCITY;

        else if (endState.equals(SystemsState.ARM_GROUND))
            combinedVelocity = TO_BELLY_VELOCITY;

        else {
            double startLength = startingNode.getExtendPosition() + STARTING_LENGTH;
            double endLength = endNode.getExtendPosition() + STARTING_LENGTH;
            double distance = GBMath.distance(
                    GBMath.convertToCartesian(startLength, startingNode.getAnglePosition()),
                    GBMath.convertToCartesian(endLength, endNode.getAnglePosition())
            );
            combinedVelocity = STATIC_COMBINED_VELOCITY * GBMath.sigmoid(distance, 1, 5.9, 0.3);
        }

        Logger.getInstance().recordOutput("Arm/TargetPose3D/endNode", ArmSimulation.getArmPosition(endNode.getExtendPosition(), (endNode.getAnglePosition())));
    }

    public double calculateExtenderVelocity(double ratio) {
        double signOfExtender = Math.signum(endNode.getExtendPosition() - extender.getLength());
        double extenderVelocity = Math.sqrt(combinedVelocity * combinedVelocity / (ratio * ratio + 1));
        double wantedVelocity = signOfExtender * extenderVelocity;

        wantedVelocity = limit(wantedVelocity, MAX_EXTENDER_VELOCITY);

        return wantedVelocity;
    }

    public double calculateAngularVelocity(double startVelocity) {
        double signOfAngle = Math.signum(endNode.getAnglePosition() - elbow.getAngleRadians());
        double magnitudeOfVelocity = startVelocity / (extender.getLength() + STARTING_LENGTH);

        magnitudeOfVelocity = limit(magnitudeOfVelocity, MAX_ANGULAR_VELOCITY);

        return signOfAngle * Math.abs(magnitudeOfVelocity);
    }

    public void moveArm() {
        double startLength = extender.getLength() + STARTING_LENGTH;
        double endLength = endNode.getExtendPosition() + STARTING_LENGTH;
        double angleBetween = endNode.getAnglePosition() - elbow.getAngleRadians();

        double ratio = GBMath.getRatioBetweenAngleAndLength(startLength, endLength, angleBetween);

        double extenderVelocity = calculateExtenderVelocity(ratio);
        double angularVelocity = calculateAngularVelocity(ratio * extenderVelocity);

        if (!endNode.isAtAngle(elbow.getAngleRadians()))
            elbow.setMotorVoltage(Elbow.getDynamicFeedForward(angularVelocity, extender.getLength(), elbow.getAngleRadians()));
        else
            elbow.setMotorVoltage(Elbow.getStaticFeedForward(extender.getLength(), elbow.getAngleRadians()));


        if ((endLength > EXTENDER_STOPPING_THRESHOLD || startLength > EXTENDER_STOPPING_THRESHOLD)
                && !endNode.isAtLength(extender.getLength()))
            extender.setMotorVoltage(Extender.getDynamicFeedForward(extenderVelocity, Extender.getDynamicFeedForward(extenderVelocity, Elbow.getInstance().getAngleRadians())));
        else
            extender.setMotorVoltage(Extender.getStaticFeedForward(elbow.getAngleRadians()));

    }
    @Override
    public void execute() {
        moveArm();
    }

    @Override
    public boolean isFinished() {
        return endNode.isAtNode(elbow.getAngleRadians(), extender.getLength());
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            system1MidNode.setMidNode(startState, endState);
            system1CurrentNode.setCurrentNode(SystemsState.MID_NODE_1);
        } else
            system1CurrentNode.setCurrentNode(endState);
    }
}
