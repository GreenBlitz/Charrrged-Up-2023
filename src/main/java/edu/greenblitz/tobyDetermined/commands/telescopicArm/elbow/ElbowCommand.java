package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class ElbowCommand extends GBCommand {

    protected Elbow elbow;

    public ElbowCommand (){
        elbow = Elbow.getInstance();
        require(elbow);
    }

    @Override
    public void initialize() {
        super.initialize();
        elbow.isUsed = true;
        SmartDashboard.putBoolean("ended", false);

    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        elbow.isUsed = false;
        SmartDashboard.putBoolean("ended", true);
    }
}
