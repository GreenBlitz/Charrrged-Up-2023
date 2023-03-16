package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class QuasiStaticFeedForward extends ExtenderCommand{
	
	private double velocity, rampRate, targetVel;
	
	public QuasiStaticFeedForward(double rampRate){
		velocity = 0.5;
		this.rampRate = rampRate;
		SmartDashboard.putNumber("extender targetVel", 0.002);
	}
	
	@Override
	public void initialize() {
		super.initialize();
		velocity = 0.5;
		targetVel = 0.01;
		targetVel = targetVel * -1;
	}
	
	@Override
	public void execute() {
		super.execute();
		velocity += rampRate;
		extender.setMotorVoltage(-velocity);
		SmartDashboard.putNumber("extender volt", velocity);
		System.out.println("extender volt: " + velocity);
		SmartDashboard.putNumber("ext velocity", extender.getVelocity());
		System.out.println("velocity: " + (long)extender.getVelocity());
		System.out.println("targetvel" + targetVel);
	}
	
	@Override
	public boolean isFinished() {
		SmartDashboard.putNumber("arm velocity", extender.getVelocity());
//		return super.isFinished() || Math.abs(extender.getVelocity()) > targetVel;
		return super.isFinished() || extender.getVelocity() < targetVel;
	}
	
	@Override
	public void end(boolean interrupted) {
		System.out.println("**************************");
		super.end(interrupted);
		extender.setMotorVoltage(0);
		SmartDashboard.putNumber("extender ks+kg", velocity);
		velocity = 0;
	}
}