package edu.greenblitz.pegasus.commands.swerve.measurement;

import edu.greenblitz.pegasus.OI;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.swerve.SwerveCommand;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveModule;
import edu.greenblitz.pegasus.utils.Dataset;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class CalibrateLampreyByNeo extends SwerveCommand {

	private Dataset lampreyToNeoTicks;
	private SwerveChassis.Module module;

	private static final double NEO_TICK_VALUE = 0.024933276697993; //supposed to be 1/42 but empiricly this is the value

	public CalibrateLampreyByNeo(SwerveChassis.Module module){
		lampreyToNeoTicks = new Dataset(2);
		this.module = module;
	}

	@Override
	public void initialize() {
		super.initialize();
		swerve.resetAllEncoders();
	}

	@Override
	public void execute() {
		super.execute();
		SmartDashboard.putNumber("unrounded neo tick value", swerve.getModuleAngle(module));
		int currNeoTicks = (int)Math.round(swerve.getModuleAngle(module) / NEO_TICK_VALUE);
		SmartDashboard.putNumber("curr neo tick", currNeoTicks);
		if (!lampreyToNeoTicks.containsValue(new double[]{currNeoTicks})){
			lampreyToNeoTicks.addDatapoint(swerve.getModuleAbsoluteValue(module), new double[]{currNeoTicks});
		} //todo take the min value in case of clash
	}

	@Override
	public boolean isFinished() {
		for (int i = 0; i < 252; i++) {
			if (!lampreyToNeoTicks.containsValue(new double[]{i}) && !OI.getInstance().getMainJoystick().A.get()){
				SmartDashboard.putNumber("last stopped", i);
				return false;
			}
		}
		return true;
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		try {
			PrintWriter out = new PrintWriter("/home/lvuser/"+module+".txt"); //overwrites existing file
			out.println(lampreyToNeoTicks);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
