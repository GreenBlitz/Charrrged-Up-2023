package edu.greenblitz.tobyDetermined.commands.LED;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RainbowLED extends SequentialCommandGroup {

	private static int Hue = 0;
	private static int index = 0;
	public RainbowLED(){
		super(new SetLEDColor(index, Color.fromHSV(Hue, 100, 100)),
				new WaitCommand(RobotMap.LED.RAINBOW_TIME));

		Hue += 5;
		Hue %= 360;

		index += 1;
		index %= RobotMap.LED.LENGTH;
	}

}
