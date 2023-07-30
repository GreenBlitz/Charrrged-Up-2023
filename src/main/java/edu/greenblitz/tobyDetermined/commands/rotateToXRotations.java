package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.DoSomthing;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class rotateToXRotations extends GBCommand {
	
	protected DoSomthing j;
	private double num;
	private double tol;
	
	public rotateToXRotations(double num, double tol) {
		this.tol = tol;
		this.num = num;
		System.out.println("Created command");
		j = DoSomthing.getInstance();
		require(j);
	}
	
	@Override
	public void execute() {
		System.out.println("In command");
		j.rotate3times(num);
		SmartDashboard.putNumber("motor position", j.getEncoderPosition());
	}
	
	@Override
	public boolean isFinished() {
		
		return j.getEncoderPosition() >= num - tol && j.getEncoderPosition() <= num + tol;
	}
	
	@Override
	public void end(boolean interrupted) {
		j.setPower(0);
		
	}
}
