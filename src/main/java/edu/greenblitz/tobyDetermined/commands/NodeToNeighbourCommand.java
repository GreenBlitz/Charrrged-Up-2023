package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NodeToNeighbourCommand extends GBCommand {
    private final Extender extender;
    private final Elbow elbowSub;
    private NodeArm start;
    private NodeArm end;
    private double TOTAL_VELOCITY = 0.2;
    private double MAX_EXTENDER_VELOCITY = 0.2; //In Meters Per Second
    private double MAX_ANGULAR_VELOCITY = Units.degreesToRadians(20);


    public NodeToNeighbourCommand(NodeArm start, NodeArm end){
        extender = Extender.getInstance();
        elbowSub = Elbow.getInstance();
        require(elbowSub);
        require(extender);
        this.start = start;
        this.end = end;
    }
    public static double cosineRule(double sideA, double sideB, double angleBetweenSideAndSideB) {
        return Math.sqrt(sideA*sideA+sideB*sideB-2*sideA*sideB*Math.cos(angleBetweenSideAndSideB));
    }
    public static double getRatioBetweenAngleAndLength(double a, double b, double gamma) {
        double c = cosineRule(a,b,gamma);
        double beta = Math.asin(b/c*Math.sin(gamma));
        return Math.tan(beta);
    }

    public void moveArm(double velocityToAngle, NodeArm nodeEndIndex){// untested
        double start = extender.getLength();
        double end = nodeEndIndex.getExtendPos();
        double gamma = nodeEndIndex.getAnglePos()-elbowSub.getAngleRadians();
        double ratio = getRatioBetweenAngleAndLength(start,end,gamma);
        double extenderVelocity = Math.sqrt(velocityToAngle*velocityToAngle/(ratio*ratio+1));
        double angularVelocity = ratio*extenderVelocity;
        extenderVelocity = Math.min(MAX_EXTENDER_VELOCITY,extenderVelocity);
        extenderVelocity = Math.max(-MAX_EXTENDER_VELOCITY,extenderVelocity);
        angularVelocity = Math.min(MAX_ANGULAR_VELOCITY,angularVelocity);
        angularVelocity = Math.max(-MAX_ANGULAR_VELOCITY,angularVelocity);
        if (!NodeBase.getInstance().getIfInAngle(elbowSub.getAngleRadians(),nodeEndIndex))
            elbowSub.setAngSpeed(angularVelocity, elbowSub.getAngleRadians(), extender.getLength());
        else
            elbowSub.setAngSpeed(0,elbowSub.getAngleRadians(),extender.getLength());
        if (!NodeBase.getInstance().getIfInLength(extender.getLength(),nodeEndIndex))
            extender.setLinSpeed(extenderVelocity, elbowSub.getAngleRadians());
        else
            extender.setLinSpeed(0,elbowSub.getAngleRadians());
    }

    public boolean isInPlace(NodeArm target){
        return NodeBase.getInstance().getIfInNode(elbowSub.getAngleRadians(),extender.getLength(), target );
    }


    @Override
    public void execute() {
        if(start.getNeighbors().contains(end)) {
            moveArm(TOTAL_VELOCITY, end);
        }
    }

    @Override
    public boolean isFinished() {
        return isInPlace(end);
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putNumber("passIsFinished",SmartDashboard.getNumber("passIsFinished",0)+1);
    }
}
