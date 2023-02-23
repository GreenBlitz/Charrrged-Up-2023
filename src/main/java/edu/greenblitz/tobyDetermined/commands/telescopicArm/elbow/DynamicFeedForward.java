package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtenderCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

public class DynamicFeedForward extends ElbowCommand {
    private double velocity, rampRate, ksAndKg;

    private ArrayList<Double> readings;

    public DynamicFeedForward(double rampRate, double ksAndKg){
        readings = new ArrayList<>();
        velocity = 0;
        this.rampRate = rampRate;
        this.ksAndKg = ksAndKg;
    }

    @Override
    public void initialize() {
        super.initialize();

    }

    @Override
    public void execute() {
        super.execute();
        velocity += rampRate;
        elbow.setMotorVoltage(velocity);
        readings.add( (velocity - ksAndKg)/elbow.getVelocity());
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || elbow.isAtAngle(rampRate > 0 ? RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT : RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        double sum = 0;
        for (double toSum : readings){
            sum += toSum;
        }
        elbow.setMotorVoltage(0);
        SmartDashboard.putNumber("elbow kv", sum/readings.size());
    }

}
