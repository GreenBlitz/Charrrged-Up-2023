package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.RoborioUtils;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.CONSTRAINTS;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.PID;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.kS;

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
        pidController = new ProfiledPIDController(PID.getKp(), PID.getKi(), PID.getKd(), CONSTRAINTS);
        SmartDashboard.putNumber("kp", SmartDashboard.getNumber("kp", pidController.getP()));
        SmartDashboard.putNumber("ki", SmartDashboard.getNumber("ki", pidController.getI()));
        SmartDashboard.putNumber("kd", SmartDashboard.getNumber("kd", pidController.getD()));
        legalGoalAngle = elbow.getLegalGoalAngle(wantedAngle);
        pidController.reset(new TrapezoidProfile.State(elbow.getAngleRadians(), elbow.getVelocity()));
    }

    @Override
    public void execute() {
        pidController.setP(SmartDashboard.getNumber("kp", pidController.getP()));
        pidController.setI(SmartDashboard.getNumber("ki", pidController.getI()));
        pidController.setD(SmartDashboard.getNumber("kd", pidController.getD()));
        legalGoalAngle = elbow.getLegalGoalAngle(wantedAngle);
        pidController.setGoal(legalGoalAngle);
        double pidGain = pidController.calculate(elbow.getAngleRadians(), legalGoalAngle);
        SmartDashboard.putNumber("vel target", pidController.getSetpoint().velocity);
        SmartDashboard.putNumber("pos target", pidController.getSetpoint().position);
        double feedForward = Elbow.getStaticFeedForward( Extender.getInstance().getLength(), elbow.getAngleRadians()) + Math.signum(pidGain) * kS;
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
