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
import static edu.greenblitz.utils.PolarArmStraightLineUtil.*;


public class StraightLineMotion extends GBCommand {
    private final Extender extender;
    private final Elbow elbow;
    private final SystemsState startState;
    private final SystemsState endState;
    private final NodeArm startingNode;
    private final NodeArm endNode;
    private double combinedVelocity;
    private static final double STATIC_COMBINED_VELOCITY = 1; //Meters Per Second
    private static final double TO_BELLY_VELOCITY = 0.2;
    private static final double ONLY_ELBOW_VELOCITY = 1;

    //For more info on sigmoid: https://docs.google.com/document/d/1Nud3wK2SweWcnkfXrOZfS50eTqnsolggD3qc4dRBGaI/edit?usp=sharing
    private static final double SIGMOID_SIZE = 1;
    private static final double SIGMOID_UNIFORMITY = 5.9;
    private static final double SIGMOID_MOVEMENT = 0.3;
    private static final String LOGGER_TAB = "Arm/TargetPose3D/endNode";

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
            combinedVelocity = STATIC_COMBINED_VELOCITY * GBMath.sigmoid(distance, SIGMOID_SIZE, SIGMOID_UNIFORMITY, SIGMOID_MOVEMENT);
        }

        Logger.getInstance().recordOutput(LOGGER_TAB, ArmSimulation.getArmPosition(endNode.getExtendPosition(), (endNode.getAnglePosition())));
    }

    public void activateExtender(double velocity, double currentLength, double currentAngle, double endLength){
        if ((endLength + STARTING_LENGTH > EXTENDER_STOPPING_THRESHOLD || currentLength + STARTING_LENGTH > EXTENDER_STOPPING_THRESHOLD)
                && !endNode.isAtLength(currentLength))
            extender.setMotorVoltage(Extender.getDynamicFeedForward(velocity,currentAngle));
        else
            extender.setMotorVoltage(Extender.getStaticFeedForward(currentAngle));
    }

    public void activateElbow(double velocity, double currentLength, double currentAngle){
        if (!endNode.isAtAngle(currentAngle))
            elbow.setMotorVoltage(Elbow.getDynamicFeedForward(velocity, currentLength, currentAngle));
        else
            elbow.setMotorVoltage(Elbow.getStaticFeedForward(currentLength, currentAngle));
    }

    public void moveArm() {
        double currentLength = extender.getLength();
        double endLength = endNode.getExtendPosition();

        double currentAngle = elbow.getAngleRadians();
        double endAngle = endNode.getAnglePosition();
        double angleBetween = endAngle - currentAngle;

        double ratio = getRatioBetweenAngleAndLength(currentAngle + STARTING_LENGTH, endAngle + STARTING_LENGTH, angleBetween);

        double extenderVelocity = calculateExtenderVelocity(ratio, currentLength, endLength, combinedVelocity);
        double angularVelocity = calculateAngularVelocity(ratio * extenderVelocity, currentAngle, endAngle, currentLength);

        activateElbow(angularVelocity, currentLength, currentAngle);
        activateExtender(extenderVelocity, currentLength, currentAngle, endLength);
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
