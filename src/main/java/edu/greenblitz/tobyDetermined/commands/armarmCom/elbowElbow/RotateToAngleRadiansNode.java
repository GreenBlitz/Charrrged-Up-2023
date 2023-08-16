package edu.greenblitz.tobyDetermined.commands.armarmCom.elbowElbow;

import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.armarm.ExtenderSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.*;

public class RotateToAngleRadiansNode extends ElbowCommandElbow{
    private double legalGoalAngle;
    private double wantedAngle;
    private ProfiledPIDController pidController;
    private Timer timer;

    public RotateToAngleRadiansNode(double angle){
        wantedAngle = angle;
        timer = new Timer();
    }


    @Override
    public void initialize() {
        super.initialize();
        pidController = new ProfiledPIDController(PID.getKp(), PID.getKi(), PID.getKd(), CONSTRAINTS);
        legalGoalAngle = elbow.getLegalGoalAngle(wantedAngle);
        pidController.reset(new TrapezoidProfile.State(elbow.getAngleRadians(), elbow.getVelocity()));
    }

    @Override
    public void execute() {
        legalGoalAngle = elbow.getLegalGoalAngle(wantedAngle);
        pidController.setGoal(legalGoalAngle);
        double pidGain = pidController.calculate(elbow.getAngleRadians(), legalGoalAngle);
        double feedForward = ElbowSub.getStaticFeedForward( ExtenderSub.getInstance().getLength(), elbow.getAngleRadians()) + Math.signum(pidGain) * kS;
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
