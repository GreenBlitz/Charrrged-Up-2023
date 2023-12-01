package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.AStar;
import edu.greenblitz.tobyDetermined.Nodesssss.CurrentNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ObjectPositionByNode;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.GBMath;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import org.littletonrobotics.junction.Logger;

import java.util.LinkedList;
import java.util.function.Supplier;

public class NodeTracSupplier implements Supplier<Command> {
    private RobotMap.TelescopicArm.PresetPositions end;

    private static final double MAX_VELOCITY = 0.25;
    private static final double MAX_ACCELERATION = 0.6;

    public RobotMap.TelescopicArm.PresetPositions getEnd() {
        return end;
    }

    public NodeTracSupplier(RobotMap.TelescopicArm.PresetPositions endNode) {
        end = endNode;
    }

    public LinkedList<Pair<Double, RobotMap.TelescopicArm.PresetPositions>> getTimesOfNodes(Trajectory track, LinkedList<RobotMap.TelescopicArm.PresetPositions> nodeList) {
        LinkedList<Pair<Double, RobotMap.TelescopicArm.PresetPositions>> pairList = new LinkedList<>();
        int indexOfLastNodeState = 0;
        double leastDistance;
        double distanceOfNodeToState;
        for (int i = 0; i < nodeList.size(); i++) {
            leastDistance = Double.MAX_VALUE;
            for (int j = indexOfLastNodeState; j < track.getStates().size(); j++) {
                distanceOfNodeToState = GBMath.distance(track.getStates().get(j).poseMeters.getTranslation(),
                        GBMath.convertToCartesian(
                                NodeBase.getNode(nodeList.get(i)).getExtendPos(),
                                NodeBase.getNode(nodeList.get(i)).getAnglePos())
                );
                if (leastDistance > distanceOfNodeToState) {
                    leastDistance = distanceOfNodeToState;
                    indexOfLastNodeState = j;
                }
            }
            pairList.add(new Pair<>(track.getStates().get(indexOfLastNodeState).timeSeconds, nodeList.get(i)));
        }
        return pairList;
    }

    public LinkedList<Translation2d> convertPathToTrans2d(LinkedList<RobotMap.TelescopicArm.PresetPositions> path) {
        LinkedList<Translation2d> Trans2dList = new LinkedList<>();
        for (RobotMap.TelescopicArm.PresetPositions presetPositions : path) {
            Trans2dList.add(GBMath.convertToCartesian(
                    NodeBase.getNode(presetPositions).getExtendPos() + RobotMap.TelescopicArm.Extender.STARTING_LENGTH,
                    NodeBase.getNode(presetPositions).getAnglePos()
            ));
        }
        return Trans2dList;
    }

    public Command get() {
        RobotMap.TelescopicArm.PresetPositions start = CurrentNode.getCurrentNode();
        if (end.equals(start))
            return new GBCommand() {};

        LinkedList<RobotMap.TelescopicArm.PresetPositions> path = AStar.getPath(start, end);
        LinkedList<Translation2d> cartesianNodeList = convertPathToTrans2d(path);

        Translation2d firstNode = GBMath.convertToCartesian(Extender.getInstance().getLength() + RobotMap.TelescopicArm.Extender.STARTING_LENGTH, Elbow.getInstance().getAngleRadians());
        Translation2d secondNode;
        Translation2d secondToLastNode;
        Translation2d lastNode = cartesianNodeList.getLast();
        cartesianNodeList.removeLast();
        secondToLastNode = cartesianNodeList.getLast();
        if (cartesianNodeList.size() < 2)
            secondNode = lastNode;
        else
            secondNode = cartesianNodeList.get(1);
        cartesianNodeList.removeFirst();

        Rotation2d firstRotation = Rotation2d.fromRadians(Math.tan(
                (firstNode.getY() - secondNode.getY()) / (firstNode.getX() - secondNode.getX())
        ));
        Rotation2d lastRotation = Rotation2d.fromRadians(Math.tan(
                (lastNode.getY() - secondToLastNode.getY()) / (lastNode.getX() - secondToLastNode.getX())
        ));
        var trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(firstNode, firstRotation),
                cartesianNodeList,
                new Pose2d(lastNode, lastRotation),
                new TrajectoryConfig(MAX_VELOCITY, MAX_ACCELERATION)
        );
        Logger.getInstance().recordOutput("Trajectory", trajectory);
        return new MoveArmByTrajectory(trajectory, getTimesOfNodes(trajectory, path)).andThen(ObjectPositionByNode.getCommandFromState(NodeBase.getNode(end).getClawPos()));
    }
}
