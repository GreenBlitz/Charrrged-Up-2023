package edu.greenblitz.tobyDetermined.commands.telescopicArm;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.math.controller.ProfiledPIDController;
import org.opencv.core.Mat;

public class StraightLineMotion {

	private Extender extender;
	private ElbowSub elbowSub;
	private StraightLineMotion() {
		extender = Extender.getInstance();
		elbowSub = ElbowSub.getInstance();
	}
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}
	public static double getDivisionary(double a, double b,double gamma) {
		double c = Math.sqrt(a*a+b*b-2*a*b*Math.cos(gamma));
		double beta = Math.asin(b/c*Math.sin(gamma));
		return Math.tan(beta);
	}
	public void moveHandToPoint() {
	
	}
}

