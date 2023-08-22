package edu.greenblitz.tobyDetermined.commands.telescopicArm;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class StraightLineMotion {

	private Extender extender;
	private ElbowSub elbowSub;
	private NodeBase nodeBase;

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
		nodeBase = NodeBase.getInstance();
	}
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}
	public static double getDivisionary(double a, double b,double gamma) {
		double c = Math.sqrt(a*a+b*b-2*a*b*Math.cos(gamma));
		double beta = Math.asin(b/c*Math.sin(gamma));
		return Math.tan(beta);
	}
	public void moveArm(double velocityToAngle, int nodeEndIndex, int nodeStartIndex, double gamma){
		double start = nodeBase.getNode(nodeStartIndex).getExtendPos();
		double end = nodeBase.getNode(nodeEndIndex).getExtendPos();
		double ratio = getDivisionary(start,end,gamma);
		extender.setLinSpeed(velocityToAngle*ratio, elbowSub.getAngleRadians());
	}

	public void resetMotors(){
		extender.stop();
		elbowSub.stop();
	}

}

