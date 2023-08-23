package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
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
	public static double Division(double a, double b, double gamma) {
		double c = Math.sqrt(a*a+b*b-2*a*b*Math.cos(gamma));
		double beta = Math.asin(b/c*Math.sin(gamma));
		return Math.tan(beta);
	}

	public void moveArm(double velocityToAngle, NodeArm nodeEndIndex, double gamma){
		double start = extender.getLength();
		double end = nodeEndIndex.getExtendPos();
		double ratio = Division(start,end,gamma);
		elbowSub.setAngSpeed(velocityToAngle, elbowSub.getAngleRadians(), extender.getLength());
		extender.setLinSpeed(velocityToAngle/ratio, elbowSub.getAngleRadians());
	}

	public boolean isInPlace(NodeArm target){
		return NodeBase.getInstance().getIfInNode(elbowSub.getAngleRadians(),extender.getLength(), target );
	}

	public void stopMotors(){
		extender.stop();
		elbowSub.stop();
	}

}

