package edu.greenblitz.utils;

import edu.greenblitz.tobyDetermined.commands.Auto.PathFollowerBuilder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class AutonomousSelector {
	static private AutonomousSelector instance; //i did some shenanigan with the static private hehe
	private SendableChooser<AutonomousPaths> chooser = new SendableChooser<>();
	
	private AutonomousSelector(){
		//         m_chooser.addOption(auto name, path name );
		chooser.addOption("Bjust_place", AutonomousPaths.BOTTOM_JUST_PLACE);
		chooser.addOption("Bplace_exit", AutonomousPaths.BOTTOM_PLACE_EXIT);
		chooser.addOption("B13_ramp", AutonomousPaths.BOTTOM_PICK_RAMP);
		chooser.addOption("B13_a_23", AutonomousPaths.BOTTOM_TWO_OBJ);
		chooser.addOption("Mjust_place", AutonomousPaths.MIDDLE_JUST_PLACE);
		chooser.addOption("Mjust_ramp", AutonomousPaths.MIDDLE_JUST_RAMP);
		chooser.addOption("M_ramp", AutonomousPaths.MIDDLE_PLACE_RAMP);
		chooser.addOption("M_out_ramp", AutonomousPaths.MIDDLE_OUT_RAMP);
		chooser.addOption("Tjust_place", AutonomousPaths.TOP_JUST_PLACE);
		chooser.addOption("Tplace_exit", AutonomousPaths.TOP_PLACE_EXIT);
		chooser.addOption("T93_ramp", AutonomousPaths.TOP_PICK_RAMP);
		chooser.addOption("T93_d_83", AutonomousPaths.TOP_TWO_OBJ);



		ShuffleboardTab tab = Shuffleboard.getTab("auto");
		tab.add("autonomous chooser", chooser);
	}

	public AutonomousPaths getChosenValue (){
		if(chooser.getSelected() == null){return AutonomousPaths.NONE;}
		else {
			return chooser.getSelected();
		}
	}

	public static AutonomousSelector getInstance () {
		if (instance == null) {
			instance = new AutonomousSelector();
		}
		return instance;
	}

	public enum AutonomousPaths{
		MIDDLE_JUST_PLACE(PathFollowerBuilder.getInstance().followPath("Mjust_place")),
		TOP_JUST_PLACE(PathFollowerBuilder.getInstance().followPath("Tjust_place")),
		BOTTOM_JUST_PLACE(PathFollowerBuilder.getInstance().followPath("Bjust_place")),
		MIDDLE_PLACE_RAMP(PathFollowerBuilder.getInstance().followPath("M_ramp")),
		BOTTOM_PLACE_EXIT(PathFollowerBuilder.getInstance().followPath("Bplace_exit")),
		TOP_PLACE_EXIT(PathFollowerBuilder.getInstance().followPath("Tplace_exit")),
		BOTTOM_TWO_OBJ(PathFollowerBuilder.getInstance().followPath("B13_a_23")),
		TOP_TWO_OBJ(PathFollowerBuilder.getInstance().followPath("T93_d_83")),
		TOP_PICK_RAMP(PathFollowerBuilder.getInstance().followPath("T93_ramp")),
		BOTTOM_PICK_RAMP(PathFollowerBuilder.getInstance().followPath("B13_ramp")),
		MIDDLE_OUT_RAMP(PathFollowerBuilder.getInstance().followPath("M_out_ramp")),
		MIDDLE_JUST_RAMP(PathFollowerBuilder.getInstance().followPath("Mjust_ramp")),
		
		

		NONE(new InstantCommand());


		public CommandBase autonomousCommand;
		private AutonomousPaths (CommandBase autonomousCommands){
			autonomousCommand = autonomousCommands;
		}
	}

	private static CommandBase getPathTCommand (String path){
		return PathFollowerBuilder.getInstance().followPath(path);
	}
}
