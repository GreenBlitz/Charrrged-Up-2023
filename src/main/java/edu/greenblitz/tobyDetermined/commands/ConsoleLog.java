package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ConsoleLog extends InstantCommand {
	private String title;
	private String message;

	public ConsoleLog(String title, String message){
		this.title= title;
		this.message = message;
	}
	@Override
	public void initialize() {
		Console.log(title,message);
	}
}
