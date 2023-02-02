package edu.greenblitz.utils;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class AutoSelector {
	static private AutoSelector instance; //i did some shenanigan with the static private hehe
	private SendableChooser<String> chooser = new SendableChooser<>();
	
	private AutoSelector (){
		//         m_chooser.addOption(auto name, path name );
		chooser.addOption("1 meter", "1 meter");
		chooser.addOption("3 ball auto","3 ball auto");
		chooser.addOption("new", "new"); //this is the name of the path
		chooser.setDefaultOption("default","1 meter");
		SmartDashboard.putData("chooser",chooser);
	}

	public String getChosenValue (){
		return chooser.getSelected();
	}

	public static AutoSelector getInstance () {
		if (instance == null) {
			instance = new AutoSelector();
		}
		return instance;
	}
}
