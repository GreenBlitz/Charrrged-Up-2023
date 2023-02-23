package edu.greenblitz.utils;

import edu.greenblitz.tobyDetermined.commands.Auto.PathFollowerBuilder;
import edu.greenblitz.tobyDetermined.commands.swerve.AdvancedBalanceOnRamp;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutonomousSelector {
	static private AutonomousSelector instance; //i did some shenanigan with the static private hehe
	private SendableChooser<AutonomousPaths> chooser = new SendableChooser<>();
	
	private AutonomousSelector(){
		//         m_chooser.addOption(auto name, path name );

	/*	chooser.addOption("bottom three objects", AutonomousPaths.BOTTOM_THREE_OBJECTS);
		chooser.addOption("bottom only 2", AutonomousPaths.BOTTOM_ONLY_2);
		chooser.addOption("bottom two & ramp", AutonomousPaths.BOTTOM_TWO_AND_RAMP);

		chooser.addOption("top three objects",AutonomousPaths.TOP_THREE_OBJECTS);
		chooser.addOption("top two & ramp", AutonomousPaths.TOP_2_AND_RAMP);
		chooser.addOption("top only 2", AutonomousPaths.TOP_ONLY_2);*/

		chooser.addOption("middle ramp",AutonomousPaths.MIDDLE_RAMP);

		chooser.addOption("preginal 2 obj", AutonomousPaths.PREGINAL_2_OBJ);

		chooser.setDefaultOption("middle ramp",AutonomousPaths.MIDDLE_RAMP);



		ShuffleboardTab tab = Shuffleboard.getTab("auto");
		tab.add("autonomous chooser", chooser);
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
		TOP_THREE_OBJECTS(getPathTCommand("T93_d_83").andThen(getPathTCommand("T83_c_82"))),
		TOP_2_AND_RAMP(getPathTCommand("T93_d_83").andThen(getPathTCommand("T83_ramp"))),
		TOP_ONLY_2(getPathTCommand("T93_d_83")),
		//bottom
		BOTTOM_THREE_OBJECTS(getPathTCommand("B13_a_23").andThen(getPathTCommand("B23_b_22"))),
		BOTTOM_TWO_AND_RAMP(getPathTCommand("B13_a_23").andThen(getPathTCommand("B23_ramp"))),
		BOTTOM_ONLY_2(getPathTCommand("B13_a_23")),
		//middle
		MIDDLE_RAMP(getPathTCommand("M_ramp").andThen(new AdvancedBalanceOnRamp(true))),
		PREGINAL_2_OBJ(getPathTCommand("preginal, 2 obj and ramp").andThen(new AdvancedBalanceOnRamp(false)));
		public CommandBase autonomousCommand;
		private AutonomousPaths (CommandBase autonomousCommands){
			autonomousCommand = autonomousCommands;
		}
	}

	private static CommandBase getPathTCommand (String path){
		return PathFollowerBuilder.getInstance().followPath(path);
	}
}
