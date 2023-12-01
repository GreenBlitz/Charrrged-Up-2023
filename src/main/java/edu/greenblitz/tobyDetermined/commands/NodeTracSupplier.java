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

    public RobotMap.TelescopicArm.PresetPositions getEnd() {
        return end;
    }

    public NodeTracSupplier(RobotMap.TelescopicArm.PresetPositions endNode) {
        end = endNode;
        SmartDashboard.putNumber("passIsFinished", 0);
    }

    public LinkedList<Pair<Double, RobotMap.TelescopicArm.PresetPositions>> getTimesOfNodes(Trajectory track, LinkedList<RobotMap.TelescopicArm.PresetPositions> nodeList) {
        LinkedList<Pair<Double, RobotMap.TelescopicArm.PresetPositions>> pairList = new LinkedList<>();
        int indexOfLastNodeState = 0;
        double leastDistance = Double.MAX_VALUE;
        double distanceOfNodeToState;
        for (int i = 0; i < nodeList.size(); i++) {
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
            pairList.add(new Pair<>(track.getStates().get(indexOfLastNodeState).timeSeconds,nodeList.get(i)));
        }
        printListOfPairs(pairList);//debugging
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
            return new GBCommand() {
            };
        LinkedList<RobotMap.TelescopicArm.PresetPositions> path = AStar.getPath(start, end);
        LinkedList<Translation2d> cartesianList = convertPathToTrans2d(path);

        Translation2d first = GBMath.convertToCartesian(Extender.getInstance().getLength() + RobotMap.TelescopicArm.Extender.STARTING_LENGTH, Elbow.getInstance().getAngleRadians());
        Translation2d second;
        Translation2d secondToLast;
        Translation2d last = cartesianList.getLast();
        cartesianList.removeLast();
        secondToLast = cartesianList.getLast();
        if (cartesianList.size() < 2)
            second = last;
        else
            second = cartesianList.get(1);

        cartesianList.removeFirst();
        Rotation2d firstRotation = Rotation2d.fromRadians(Math.tan(
                (first.getY() - second.getY()) / (first.getX() - second.getX())
        ));
        Rotation2d lastRotation = Rotation2d.fromRadians(Math.tan(
                (last.getY() - secondToLast.getY()) / (last.getX() - secondToLast.getX())
        ));
        var trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(first, firstRotation),
                cartesianList,//list of everything in the list except the first and last
                new Pose2d(last, lastRotation),
                new TrajectoryConfig(0.25, 0.6)//magic numbers
        );
        Logger.getInstance().recordOutput("Trajectory", trajectory);
        return new MoveArmByTrajectory(trajectory, getTimesOfNodes(trajectory, path)).andThen(ObjectPositionByNode.getCommandFromState(NodeBase.getNode(end).getClawPos()));
    }

    public static void printListOfPairs(LinkedList<Pair<Double, RobotMap.TelescopicArm.PresetPositions>> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getFirst() + "," + list.get(i).getSecond());
        }
    }
}
