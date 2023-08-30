package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.Mat;

public class NodeToNeighbourCommand extends GBCommand {
    private final Extender extender;
    private final Elbow elbowSub;
    private NodeArm start;
    private NodeArm end;
    private double VELOCITY_TO_ANGLE_MOTOR = 1; //In Radians Per Second
    private double MAX_VELOCITY = 6; //In Meters Per Second


    public NodeToNeighbourCommand(NodeArm start, NodeArm end){
        extender = Extender.getInstance();
        elbowSub = Elbow.getInstance();
        require(elbowSub);
        require(extender);
        this.start = start;
        this.end = end;
    }
    public static double cosineRule(double sideA, double sideB, double angleBetweenSideAAndSideB) {
        return Math.sqrt(sideA*sideA+sideB*sideB-2*sideA*sideB*Math.cos(angleBetweenSideAAndSideB));
    }
    public static double getRatioBetweenAngleAndLength(double a, double b, double gamma) {
        double c = cosineRule(a,b,gamma);
        double beta = Math.asin(b/c*Math.sin(gamma));
        return Math.tan(beta);
    }

    public void moveArm(double velocityToAngle, NodeArm nodeEndIndex){// not tested
        double start = extender.getLength();
        double end = nodeEndIndex.getExtendPos();
        double gamma = nodeEndIndex.getAnglePos()-elbowSub.getAngleRadians();
        double ratio = getRatioBetweenAngleAndLength(start,end,gamma);
        double extenderVelocity = velocityToAngle * extender.getLength()/ratio;
        extenderVelocity = Math.max(MAX_VELOCITY,extenderVelocity);
        elbowSub.setAngSpeed(velocityToAngle, elbowSub.getAngleRadians(), extender.getLength());
        extender.setLinSpeed(extenderVelocity, elbowSub.getAngleRadians());
    }

    public boolean isInPlace(NodeArm target){
        return NodeBase.getInstance().getIfInNode(elbowSub.getAngleRadians(),extender.getLength(), target );
    }


    @Override
    public void execute() {
        if(start.getNeighbors().contains(end)) {
            moveArm(VELOCITY_TO_ANGLE_MOTOR, end);
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
