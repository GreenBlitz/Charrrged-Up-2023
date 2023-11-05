package edu.greenblitz.utils.Autonomous;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

public class AutonomousSelector {
	static private AutonomousSelector instance; //i did some shenanigan with the static private hehe
	private SendableChooser<Command> chooser;
	
	private AutonomousSelector(){
		chooser = AutoBuilder.buildAutoChooser();

		ShuffleboardTab tab = Shuffleboard.getTab("auto");
		tab.add("autonomous chooser", chooser);
	}

	public static AutonomousSelector getInstance () {
		if (instance == null) {
			instance = new AutonomousSelector();
		}
		return instance;
	}

	public Command getAutonomousCommand(){
		return chooser.getSelected();
	}
}
