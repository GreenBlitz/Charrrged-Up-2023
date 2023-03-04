package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.RoborioUtils;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.CONSTRAINTS;

public class RotateToAngleRadians extends ElbowCommand {

    private double legalGoalAngle;
    private double wantedAngle;
    private TrapezoidProfile trapezoidProfile;
    private Timer timer;

    public RotateToAngleRadians(double angle){
        wantedAngle = angle;
        timer = new Timer();
    }


    @Override
    public void initialize() {
        super.initialize();
        legalGoalAngle = elbow.getLegalGoalAngle(wantedAngle);
        SmartDashboard.putNumber("legal goal ang", legalGoalAngle);
        trapezoidProfile = new TrapezoidProfile(CONSTRAINTS,new TrapezoidProfile.State(legalGoalAngle, 0), new TrapezoidProfile.State(elbow.getAngleRadians(), elbow.getVelocity()));
        timer.restart();
    }

    @Override
    public void execute() {
        if (legalGoalAngle != elbow.getLegalGoalAngle(wantedAngle)){
            Console.log("swapped profile", "swapped profile");
            legalGoalAngle = elbow.getLegalGoalAngle(wantedAngle);
            trapezoidProfile = new TrapezoidProfile(CONSTRAINTS,new TrapezoidProfile.State(legalGoalAngle, 0), new TrapezoidProfile.State(elbow.getAngleRadians(), elbow.getVelocity()));
            timer.restart();
        }
        TrapezoidProfile.State setpoint = trapezoidProfile.calculate(timer.get());
        double feedForward = Elbow.getStaticFeedForward(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians());
        elbow.moveTowardsAngleRadians(setpoint.position, feedForward);
        SmartDashboard.putBoolean("is at angle?", false);
    }

    @Override
    public boolean isFinished() {
        return elbow.isAtAngle(legalGoalAngle);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        elbow.stop();
        SmartDashboard.putBoolean("is at angle?", true);
    }
}
