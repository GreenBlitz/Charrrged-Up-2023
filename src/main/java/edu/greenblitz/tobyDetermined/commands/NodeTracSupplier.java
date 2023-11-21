package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.AStar;
import edu.greenblitz.tobyDetermined.Nodesssss.CurrentNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ObjectPositionByNode;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.LinkedList;
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
            Trans2dList.add(new Translation2d(
                    NodeBase.getNode(presetPositions).getExtendPos(),
                    NodeBase.getNode(presetPositions).getAnglePos()
            ));
        }
        return Trans2dList;
    }
    public Command get() {
        RobotMap.TelescopicArm.PresetPositions start = CurrentNode.getCurrentNode();
        LinkedList<RobotMap.TelescopicArm.PresetPositions> path = AStar.getPath(start,end);
        LinkedList<Translation2d> cartesianList = convertPathToTrans2d(path);
        var trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(cartesianList.getFirst(), Rotation2d.fromRadians(0)),
                cartesianList,
                new Pose2d(cartesianList.getLast(), Rotation2d.fromRadians(0)),
                new TrajectoryConfig(
                        20, 15)
        );
        return new MoveArmByTrajectory(trajectory).andThen( ObjectPositionByNode.getCommandFromState(NodeBase.getNode(end).getClawPos()));
    }

}
