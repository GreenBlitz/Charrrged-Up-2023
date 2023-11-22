package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.AStar;
import edu.greenblitz.tobyDetermined.Nodesssss.CurrentNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ObjectPositionByNode;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.GBMath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class NodeTracSupplier implements Supplier<Command> {
    private RobotMap.TelescopicArm.PresetPositions end;
    public RobotMap.TelescopicArm.PresetPositions getEnd(){
        return end;
    }

    public NodeTracSupplier(RobotMap.TelescopicArm.PresetPositions endNode) {
        end = endNode;
        SmartDashboard.putNumber("passIsFinished",0);
    }
    public LinkedList<Translation2d> convertPathToTrans2d(LinkedList<RobotMap.TelescopicArm.PresetPositions> path){
        LinkedList<Translation2d> Trans2dList = new LinkedList<>();
        for (RobotMap.TelescopicArm.PresetPositions presetPositions : path) {
            Trans2dList.add(GBMath.polarToCartesian(
                    NodeBase.getNode(presetPositions).getExtendPos()+RobotMap.TelescopicArm.Extender.STARTING_LENGTH,
                    NodeBase.getNode(presetPositions).getAnglePos()
            ));
        }
        return Trans2dList;
    }
    public Command get() {
        if (end.equals(CurrentNode.getCurrentNode()))
            return new GBCommand() {};
        RobotMap.TelescopicArm.PresetPositions start = CurrentNode.getCurrentNode();
        LinkedList<RobotMap.TelescopicArm.PresetPositions> path = AStar.getPath(start,end);
        LinkedList<Translation2d> cartesianList = convertPathToTrans2d(path);

        Translation2d first = cartesianList.getFirst();
        Translation2d last = cartesianList.getLast();

        cartesianList.removeFirst();
        cartesianList.removeLast();
        var trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(first, Rotation2d.fromRadians(0)),
                cartesianList,//list of everything in the list except the first and last
                new Pose2d(last, Rotation2d.fromRadians(0)),
                new TrajectoryConfig(0.5, 1)
        );
        return new MoveArmByTrajectory(trajectory).andThen( ObjectPositionByNode.getCommandFromState(NodeBase.getNode(end).getClawPos()));
    }

}
