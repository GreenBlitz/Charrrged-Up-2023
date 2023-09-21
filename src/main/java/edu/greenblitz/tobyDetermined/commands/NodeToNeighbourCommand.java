package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
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
    private double COMBINED_VELOCITY = 0.5;
    private double MAX_EXTENDER_VELOCITY = 0.3; //In Meters Per Second
    private double MAX_ANGULAR_VELOCITY = 0.3;//In Meters Per Second

    public NodeToNeighbourCommand(NodeArm start, NodeArm end){
        extender = Extender.getInstance();
        elbowSub = Elbow.getInstance();
        //require(elbowSub);
        //require(extender);
        this.start = start;
        this.end = end;
    }


    public static double cosineRule(double sideA, double sideB, double angleBetweenSideAndSideB) {
        return Math.sqrt(sideA*sideA+sideB*sideB-2*sideA*sideB*Math.cos(angleBetweenSideAndSideB));
    }
    public static double getRatioBetweenAngleAndLength(double a, double b, double gamma) {
        double c = cosineRule(a,b,gamma);
        double height = a*Math.sin(gamma);
        double adjacent = Math.sqrt(c*c - height*height);
        return height/adjacent;
    }

    public double calculateExtenderVelocity(double ratio, NodeArm nodeEndIndex) {
        double signOfExtender = Math.signum(nodeEndIndex.getExtendPos()-extender.getLength());
        double extenderVelocity = Math.sqrt(COMBINED_VELOCITY * COMBINED_VELOCITY /(ratio*ratio+1));
        return signOfExtender * extenderVelocity;
    }
    public double calculateAngularVelocity(double startVelocity,NodeArm nodeEndIndex){
        startVelocity = Math.min(MAX_ANGULAR_VELOCITY,startVelocity);
        startVelocity = Math.max(-MAX_ANGULAR_VELOCITY,startVelocity);
        double signOfAngle = Math.signum(nodeEndIndex.getAnglePos()-elbowSub.getAngleRadians());
        double magnitudeOfVelocity = startVelocity/extender.getLength();
        return signOfAngle*Math.abs(magnitudeOfVelocity);
    }
    public void moveArm( NodeArm nodeEndIndex){
        double start = extender.getLength();
        double end = nodeEndIndex.getExtendPos();
        double gamma = nodeEndIndex.getAnglePos()-elbowSub.getAngleRadians();
        double ratio = getRatioBetweenAngleAndLength(start,end,gamma);
        double extenderVelocity = calculateExtenderVelocity(ratio,nodeEndIndex);
        double angularVelocity = calculateAngularVelocity(ratio*extenderVelocity,nodeEndIndex);
        extenderVelocity = Math.min(MAX_EXTENDER_VELOCITY,extenderVelocity);
        extenderVelocity = Math.max(-MAX_EXTENDER_VELOCITY,extenderVelocity);
        SmartDashboard.putNumber("ratioVelocity",ratio);
        if (!(NodeBase.getInstance().getIfInAngle(elbowSub.getAngleRadians(),nodeEndIndex))){
            elbowSub.setAngSpeed(angularVelocity, elbowSub.getAngleRadians(), extender.getLength());
        }
        else
            elbowSub.stop();
        extender.setLinSpeed(extenderVelocity, elbowSub.getAngleRadians());
    }

    public boolean isInPlace(NodeArm target){
        return NodeBase.getInstance().getIfInNode(elbowSub.getAngleRadians(),extender.getLength(), target );
    }


    @Override
    public void initialize() {
//        moveLittlePoints(end);
//        curCommand = new ExtendToLength(littlePoints[0][0]);
//        elbowSub.moveTowardsAngleRadians(littlePoints[1][0]);

    }

    @Override
    public void execute() {
        if(start.getNeighbors().contains(end)) {
            moveArm(end);
        }

    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putBoolean("isInTarget",false);
        if (isInPlace(end)) {
            SmartDashboard.putBoolean("isInTarget", true);
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putNumber("passIsFinished",SmartDashboard.getNumber("passIsFinished",0)+1);
    }
}
