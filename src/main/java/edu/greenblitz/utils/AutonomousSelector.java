package edu.greenblitz.utils;

import edu.greenblitz.tobyDetermined.commands.Auto.PathFollowerBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutonomousSelector {
	static private AutonomousSelector instance; //i did some shenanigan with the static private hehe
	private SendableChooser<AutonomousPaths> chooser = new SendableChooser<>();
	
	private AutonomousSelector(){
		//         m_chooser.addOption(auto name, path name );
		chooser.addOption("bottom three objects", AutonomousPaths.BOTTOM_THREE_OBJECTS);
		chooser.addOption("bottom only 2", AutonomousPaths.BOTTOM_ONLY_2);
		chooser.addOption("bottom two & ramp", AutonomousPaths.BOTTOM_TWO_AND_RAMP);

		chooser.addOption("top three objects",AutonomousPaths.TOP_THREE_OBJECTS);
		chooser.addOption("top two & ramp", AutonomousPaths.TOP_2_AND_RAMP);
		chooser.addOption("top only 2", AutonomousPaths.TOP_ONLY_2);

		chooser.addOption("middle ramp",AutonomousPaths.MIDDLE_RAMP);

		chooser.setDefaultOption("middle ramp",AutonomousPaths.MIDDLE_RAMP);

		SmartDashboard.putData("chooser",chooser);
	}

	public AutonomousPaths getChosenValue (){
		return chooser.getSelected();
	}

	public static AutonomousSelector getInstance () {
		if (instance == null) {
			instance = new AutonomousSelector();
		}
		return instance;
	}

	public enum AutonomousPaths{
		//top
		TOP_THREE_OBJECTS(getInstance().getPathTCommand("T93_d_83-1").andThen(getInstance().getPathTCommand("T83_c_82-2"))),
		TOP_2_AND_RAMP(getInstance().getPathTCommand("T93_d_83-1").andThen(getInstance().getPathTCommand("T83_ramp"))),
		TOP_ONLY_2(getInstance().getPathTCommand("T93_d_83-1")),
		//bottom
		BOTTOM_THREE_OBJECTS(getInstance().getPathTCommand("B13_a_23-1").andThen(getInstance().getPathTCommand("b23_b_22-2"))),
		BOTTOM_TWO_AND_RAMP(getInstance().getPathTCommand("B13_a_23-1").andThen(getInstance().getPathTCommand("B23_ramp"))),
		BOTTOM_ONLY_2(getInstance().getPathTCommand("B13_a_23-1")),
		//middle
		MIDDLE_RAMP(getInstance().getPathTCommand("M_ramp"));
		public CommandBase autonomousCommand;
		private AutonomousPaths (CommandBase autonomousCommands){
			autonomousCommand = autonomousCommands;
		}
	}

	private CommandBase getPathTCommand (String path){
		return PathFollowerBuilder.getInstance().followPath(path);
	}
}
