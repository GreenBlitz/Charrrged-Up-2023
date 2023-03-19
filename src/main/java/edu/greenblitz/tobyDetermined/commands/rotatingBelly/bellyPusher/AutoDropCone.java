package edu.greenblitz.tobyDetermined.commands.rotatingBelly.bellyPusher;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.BellyPusher;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.*;

public class AutoDropCone extends ProxyCommand {
	public AutoDropCone() {
		super(new SequentialCommandGroup(
				new InstantCommand(() ->
				{
					RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH = 0.1;
					Extender.ExtenderState.IN_WALL_LENGTH.setMaxLength(RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH);
				}),
				new ParallelCommandGroup(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.PRE_CONE_DROP),
						new InstantCommand(() ->{
							Claw.getInstance().coneCatchMode();
							ObjectSelector.selectCone();
						})),
				new PushCone(),
				new GoToPosition(RobotMap.TelescopicArm.PresetPositions.POST_CONE_DROP),
				new WaitCommand(0.5)
				)
		);
	}


	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		BellyPusher.getInstance().closePiston();
		RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH = 0.054;
		Extender.ExtenderState.IN_WALL_LENGTH.setMaxLength(RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH);
		SmartDashboard.putNumber("valINeedNow", RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH);
	}

	@Override
	public void execute() {
		super.execute();
		SmartDashboard.putNumber("valINeedNow", RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH);
	}
}
