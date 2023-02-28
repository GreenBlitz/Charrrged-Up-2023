package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

public class DropCone extends ClawCommand{
    @Override
    public void initialize() {
        claw.cubeCatchMode();
    }
}
