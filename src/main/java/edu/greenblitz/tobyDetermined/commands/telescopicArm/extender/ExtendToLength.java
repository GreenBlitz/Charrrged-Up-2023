package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtenderCommand;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Extender.*;

public class ExtendToLength extends ExtenderCommand {

    private double legalGoalLength;
    private double wantedLength;
    private ProfiledPIDController pidController;

    public ExtendToLength(double length){
        wantedLength = length;
    }


    @Override
    public void initialize() {
        super.initialize();
        pidController = new ProfiledPIDController(PID.getKp(), PID.getKi(), PID.getKd(), CONSTRAINTS);
        legalGoalLength = extender.getLegalGoalLength(wantedLength);
    }

    @Override
    public void execute() {
        legalGoalLength = extender.getLegalGoalLength(wantedLength);
        pidController.setGoal(legalGoalLength);
        double feedForward = Extender.getStaticFeedForward( Elbow.getInstance().getAngleRadians());
        double pidGain = pidController.calculate(extender.getLength());
        extender.setMotorVoltage(feedForward + pidGain + Math.signum(pidGain) * kS);
    }

    @Override
    public boolean isFinished() {
        return extender.isAtLength(legalGoalLength);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        extender.stop();
    }
}
