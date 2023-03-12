package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TwoStagedReset extends SequentialCommandGroup {
	
	public static final double FORWARD_RESET_VELOCITY = 3;
	
	public TwoStagedReset(){
		super(new ResetExtender(),
				new ResetExtender(FORWARD_RESET_VELOCITY){
					@Override
					public boolean isFinished() {
						return super.isFinished() || !extender.getLimitSwitch();
					}
				});
	}
}
