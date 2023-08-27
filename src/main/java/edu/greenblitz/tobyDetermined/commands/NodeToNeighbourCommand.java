package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NodeToNeighbourCommand extends GBCommand {
    private final Extender extender;
    private final ElbowSub elbowSub;
    private NodeArm start;
    private NodeArm end;


    public NodeToNeighbourCommand(NodeArm start, NodeArm end){
        extender = Extender.getInstance();
        elbowSub = ElbowSub.getInstance();
        require(elbowSub);
        require(extender);
        this.start = start;
        this.end = end;
    }
    public static double Division(double a, double b, double gamma) {
        double c = Math.sqrt(a*a+b*b-2*a*b*Math.cos(gamma));
        double beta = Math.asin(b/c*Math.sin(gamma));
        return Math.tan(beta);
    }

    public void moveArm(double velocityToAngle, NodeArm nodeEndIndex){// problem
        double start = extender.getLength();
        double end = nodeEndIndex.getExtendPos();
        double gamma = nodeEndIndex.getAnglePos()-elbowSub.getAngleRadians();
        double ratio = Division(start,end,gamma);
        elbowSub.setAngSpeed(velocityToAngle, elbowSub.getAngleRadians(), extender.getLength());
        extender.setLinSpeed(velocityToAngle/ratio, elbowSub.getAngleRadians());
    }

    public boolean isInPlace(NodeArm target){
        return NodeBase.getInstance().getIfInNode(elbowSub.getAngleRadians(),extender.getLength(), target );
    }


    @Override
    public void execute() {
        if(start.getNeighbors().contains(end)) {
            moveArm(3, end);
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
