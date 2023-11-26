package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.Robot;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.*;

public class RotateToAngleRadians extends ElbowCommand {

    private double legalGoalAngle;
    private double wantedAngle;
    private ProfiledPIDController pidController;
    private Timer timer;

    public RotateToAngleRadians(double angle){
        wantedAngle = angle;
        timer = new Timer();
    }


    @Override
    public void initialize() {
        super.initialize();

        pidController = new ProfiledPIDController(elbow.getPID().getKp(), elbow.getPID().getKi(), elbow.getPID().getKd(), CONSTRAINTS);
        legalGoalAngle = elbow.getLegalGoalAngle(wantedAngle);
        pidController.reset(new TrapezoidProfile.State(elbow.getAngleRadians(), elbow.getVelocity()));
        elbow.setGoalAngle(legalGoalAngle);
    }

    @Override
    public void execute() {
        legalGoalAngle = elbow.getLegalGoalAngle(wantedAngle);
        pidController.setGoal(legalGoalAngle);
        double pidGain = pidController.calculate(elbow.getAngleRadians(), legalGoalAngle);
        double feedForward = Elbow.getStaticFeedForward(Extender.getInstance().getLength(), elbow.getAngleRadians()) + Math.signum(pidGain) * kS;
        elbow.debugSetPower(feedForward / Battery.getInstance().getCurrentVoltage() + pidGain);

    }

    @Override
    public boolean isFinished() {
        return elbow.isAtAngle(legalGoalAngle);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        elbow.stop();
    }
}
