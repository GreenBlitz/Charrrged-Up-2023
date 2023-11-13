//package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.calibration;
//
//import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
//import edu.greenblitz.utils.GBCommand;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class SetExtenderVelocity extends GBCommand {
//	private double wantedSpeed;
//
//	public SetExtenderVelocity(double wantedSpeed){
//		this.wantedSpeed = wantedSpeed;
//		require(Extender.getInstance());
//	}
//
//	@Override
//	public void execute() {
//		Extender.getInstance().setLinSpeed(wantedSpeed);
//		SmartDashboard.putNumber("wanted vellll", wantedSpeed);
//	}
//
//	@Override
//	public boolean isFinished() {
//		return wantedSpeed == Extender.getInstance().getVelocity();
//	}
//
//	@Override
//	public void end(boolean interrupted) {
//		Extender.getInstance().stop();
//	}
//}
