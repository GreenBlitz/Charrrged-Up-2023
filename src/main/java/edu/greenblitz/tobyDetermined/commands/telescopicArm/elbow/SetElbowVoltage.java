//package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;
//
//import edu.greenblitz.tobyDetermined.RobotMap;
//import edu.greenblitz.utils.GBCommand;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class SetElbowVoltage extends ElbowCommand {
//
//	private double volt;
//
//	public SetElbowVoltage(double v){
//		volt = v;
//	}
//
//	@Override
//	public void execute() {
//		elbow.setMotorVoltage(volt);
//	}
//
//	@Override
//	public void end(boolean interrupted) {
//		elbow.stop();
//	}
//}
