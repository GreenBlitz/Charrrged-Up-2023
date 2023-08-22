package edu.greenblitz.tobyDetermined.commands.telescopicArm;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class StraightLineMotion extends GBSubsystem {

	private final Extender extender;
	private final ElbowSub elbowSub;
	private static StraightLineMotion instance;

	public static StraightLineMotion getInstance() {
		init();
		return instance;
	}

	public static void init() {
		if (instance == null) {
			instance = new StraightLineMotion();
		}
	}
	private StraightLineMotion() {
		extender = Extender.getInstance();
		elbowSub = ElbowSub.getInstance();
	}
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}
	public static double Divisionary(double a, double b, double gamma) {
		double c = Math.sqrt(a*a+b*b-2*a*b*Math.cos(gamma));
		double beta = Math.asin(b/c*Math.sin(gamma));
		return Math.tan(beta);
	}
	public void moveArm(double velocityToAngle, NodeArm nodeEndIndex, NodeArm nodeStartIndex, double gamma){
		double start = nodeStartIndex.getExtendPos();
		double end = nodeEndIndex.getExtendPos();
		double ratio = Divisionary(start,end,gamma);
		elbowSub.setAngSpeed(velocityToAngle, elbowSub.getAngleRadians(), extender.getLength());
		extender.setLinSpeed(velocityToAngle*ratio, elbowSub.getAngleRadians());
	}

	public boolean isInPlace(NodeArm target){
		return NodeBase.getInstance().getIfInNode(elbowSub.getAngleRadians(),extender.getLength(), target );
	}

	public void resetMotors(){
		extender.stop();
		elbowSub.stop();
	}

}

