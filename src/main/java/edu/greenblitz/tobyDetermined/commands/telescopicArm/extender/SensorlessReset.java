package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class SensorlessReset extends SequentialCommandGroup {
	
	public SensorlessReset(){
		super(
				new GBCommand() {
					@Override
					public void execute() {
						Extender.getInstance().setMotorVoltage(-3);
					}
				}.raceWith(new WaitCommand(0.2)),
				new GBCommand() {
					@Override
					public void execute() {
						Extender.getInstance().setMotorVoltage(-2.5);
					}
				}.raceWith(new WaitCommand(0.8)),
				new InstantCommand(()-> Extender.getInstance().resetLength())
		);
	}
	
}
