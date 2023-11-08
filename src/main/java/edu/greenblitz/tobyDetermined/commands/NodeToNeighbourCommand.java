package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.CurrentNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
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

    private PresetPositions end;
    private static final double COMBINED_VELOCITY = 1.2; // Meters Per Second
    private static final double MAX_EXTENDER_VELOCITY = 1.5; //In Meters Per Second
    private static final double MAX_ANGULAR_VELOCITY = 3;//In Radians Per Second

    public NodeToNeighbourCommand( PresetPositions end){
        extender = Extender.getInstance();
        elbowSub = Elbow.getInstance();
        require(elbowSub);
        require(extender);
        this.end = end;
    }

    public static double getRatioBetweenAngleAndLength(double sideA, double sideB, double gamma) {
        if (sideB == 0)
            sideB+=0.02;//weird edge case where 0 * big number = 0 so messes up whole calculations
        double sideC = GBMath.lawOfCosines(sideA,sideB,gamma);
        double height = sideB*Math.sin(gamma);
        double adjacent = Math.sqrt(sideC*sideC - height*height);
        return height/adjacent;
    }

    public double calculateExtenderVelocity(double ratio, NodeArm nodeEndIndex) {
        double signOfExtender = Math.signum(nodeEndIndex.getExtendPos()-extender.getLength());
        double extenderVelocity = Math.sqrt(COMBINED_VELOCITY * COMBINED_VELOCITY /(ratio*ratio+1));
        return signOfExtender * extenderVelocity;
    }
    public double calculateAngularVelocity(double startVelocity,NodeArm nodeEndIndex){

        double signOfAngle = Math.signum(nodeEndIndex.getAnglePos()-elbowSub.getAngleRadians());
        double magnitudeOfVelocity = startVelocity/extender.getLength();
        magnitudeOfVelocity = Math.min(MAX_ANGULAR_VELOCITY,magnitudeOfVelocity);
        magnitudeOfVelocity = Math.max(-MAX_ANGULAR_VELOCITY,magnitudeOfVelocity);
        return signOfAngle * Math.abs(magnitudeOfVelocity);
    }
    public void moveArm(NodeArm nodeEndIndex){
        double start = extender.getLength();
        double end = nodeEndIndex.getExtendPos();
        double gamma = nodeEndIndex.getAnglePos()-elbowSub.getAngleRadians();
        double ratio = getRatioBetweenAngleAndLength(start,end,gamma);
        double extenderVelocity = calculateExtenderVelocity(ratio,nodeEndIndex);
        double angularVelocity = calculateAngularVelocity(ratio*extenderVelocity,nodeEndIndex);
        extenderVelocity = Math.min(MAX_EXTENDER_VELOCITY,extenderVelocity);
        extenderVelocity = Math.max(-MAX_EXTENDER_VELOCITY,extenderVelocity);
        if (!(NodeBase.getIfInAngle(elbowSub.getAngleRadians(),nodeEndIndex))){
            elbowSub.setAngSpeed(angularVelocity, elbowSub.getAngleRadians(), extender.getLength());
            SmartDashboard.putBoolean("isInThreshold",true);
        }
        else {
            elbowSub.stop();
            SmartDashboard.putBoolean("isInThreshold",false);
        }
        if (end >= 0.1 || start >= 0.1)
            extender.setLinSpeed(extenderVelocity);
        else
            extender.setMotorVoltage(Extender.getStaticFeedForward(elbowSub.getAngleRadians()));
        SmartDashboard.putNumber("wanted extender vel", extenderVelocity);
        SmartDashboard.putNumber("wanted angular vel",angularVelocity);
    }

    public boolean isInPlace(NodeArm target){
        return NodeBase.getIfInNode(elbowSub.getAngleRadians(),extender.getLength(), target );
    }

    @Override
    public void execute() {
            moveArm(NodeBase.getNode(end));
    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putBoolean("isInTarget",false);
        if (isInPlace(NodeBase.getNode(end))) {
            SmartDashboard.putBoolean("isInTarget", true);
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted)
            CurrentNode.setCurrentNode(end);
        SmartDashboard.putString("currentNode",CurrentNode.getCurrentNode().toString());
    }
}
