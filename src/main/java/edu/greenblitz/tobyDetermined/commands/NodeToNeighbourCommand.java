package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.Mat;

import java.util.ArrayList;

public class NodeToNeighbourCommand extends GBCommand {
    private final Extender extender;
    private final Elbow elbowSub;
    private NodeArm start;
    private NodeArm end;
    private double COMBINED_VELOCITY = 0.2;
    private double MAX_EXTENDER_VELOCITY = 0.2; //In Meters Per Second
    private double MAX_ANGULAR_VELOCITY = Units.degreesToRadians(20);
    private double[][] littlePoints;
    private ExtendToLength curCommand;
    private int miniIndex = 1;

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
        double beta = Math.asin(b/c*Math.sin(gamma));
        return Math.tan(beta);
    }
    public static double[][] createPoints(double x1, double y1, double x2, double y2) {
        double distance = Math.sqrt((y1-y2)*(y1-y2)+(x1-x2)*(x1-x2));
        double slope = (y2-y1)/(x2-x1);
        double yIntercept = y2-x2*slope;
        int n = 3;//to be changed
        int numOfPoints = (int)distance/n;
        ArrayList<Double> x = new ArrayList<>();
        ArrayList<Double> y = new ArrayList<>();
        if(x1>x2)
            n*=-1;
        for(int i = 0; i< numOfPoints; i++) {
            x.add(x1);
            y.add(x1*slope+yIntercept);
            x1+=n;
        }
        x.add(x2);
        y.add(x2*slope+yIntercept);
        double[][] tablePoints = new double[2][x.size()];
        double[] polarRepresentation;
        for (int i = 0; i < x.size(); i++) {
            polarRepresentation = convertToPolar(x.get(i),y.get(i));
            tablePoints[0][i] = polarRepresentation[0];
            tablePoints[1][i] = polarRepresentation[1];
        }
        return tablePoints;
    }
    public static double[] convertToCartesian(double radius, double angle) {
        // Calculate the Cartesian coordinates
        double x = radius * Math.cos(angle);
        double y = radius * Math.sin(angle);

        return new double[] { x, y };
    }
    public static double[] convertToPolar(double x,double y) {
        double radius = Math.sqrt(x*x+y*y);
        double angle = Math.atan2(y,x);
        return new double[] {radius,angle};
    }
    public void moveLittlePoints(NodeArm nodeEndIndex){
        double[] currentCords = convertToCartesian(extender.getLength(),elbowSub.getAngleRadians());
        double[] endNodeCords = convertToCartesian(nodeEndIndex.getExtendPos(),nodeEndIndex.getAnglePos());
        littlePoints = createPoints(currentCords[0],currentCords[1],endNodeCords[0],endNodeCords[1]);//polar form


    }
    public double calculateExtenderVelocity(double ratio, NodeArm nodeEndIndex) {
        double signOfExtender = Math.signum(nodeEndIndex.getExtendPos()-extender.getLength());
        double extenderVelocity = Math.sqrt(COMBINED_VELOCITY * COMBINED_VELOCITY /(ratio*ratio+1));
        return signOfExtender * extenderVelocity;
    }
    public void moveArm( NodeArm nodeEndIndex){// untested
        double start = extender.getLength();
        double end = nodeEndIndex.getExtendPos();
        double gamma = nodeEndIndex.getAnglePos()-elbowSub.getAngleRadians();
        double ratio = getRatioBetweenAngleAndLength(start,end,gamma);
        double extenderVelocity = calculateExtenderVelocity(ratio,nodeEndIndex);
        double angularVelocity = ratio*extenderVelocity;
        SmartDashboard.putNumber("Extender velocity", extenderVelocity);
        extenderVelocity = Math.min(MAX_EXTENDER_VELOCITY,extenderVelocity);
        extenderVelocity = Math.max(-MAX_EXTENDER_VELOCITY,extenderVelocity);
        angularVelocity = Math.min(MAX_ANGULAR_VELOCITY,angularVelocity);
        angularVelocity = Math.max(-MAX_ANGULAR_VELOCITY,angularVelocity);
        SmartDashboard.putBoolean("got to here", false);
        if (!NodeBase.getInstance().getIfInAngle(elbowSub.getAngleRadians(),nodeEndIndex))
            elbowSub.setAngSpeed(angularVelocity, elbowSub.getAngleRadians(), extender.getLength());
        else
            elbowSub.setAngSpeed(0,elbowSub.getAngleRadians(),extender.getLength());
        SmartDashboard.putBoolean("got to here", true);
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

//        if (curCommand.isFinished())
//        elbowSub.moveTowardsAngleRadians(littlePoints[1][miniIndex]);
//        if(littlePoints[0][miniIndex] != curCommand.getLengthGoal()){
//            elbowSub.moveTowardsAngleRadians(littlePoints[1][miniIndex]);
//        }
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
