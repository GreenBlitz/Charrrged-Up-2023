package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.kV;

public class DynamicFeedForward extends ElbowCommand {
    private double voltage;



    public DynamicFeedForward(double voltage){
        this.voltage = voltage;
        timer = new Timer();
    }

    @Override
    public void initialize() {
        super.initialize();
        timer.start();
        hasMeasureAccel = false;
    }
    
    private double lastVelocity = 0;
    private static final double dt = 0.006;
    private boolean hasMeasureAccel;
    private Timer timer;
    @Override
    public void execute() {
        SmartDashboard.putNumber("dt", dt);
        SmartDashboard.putBoolean("hasMeasuredAccel", hasMeasureAccel);
        if (timer.advanceIfElapsed(dt) && (elbow.getVelocity() > 0.05) && !hasMeasureAccel){
            double accel =  (elbow.getVelocity() - lastVelocity)/ dt;
            SmartDashboard.putNumber("accel", accel);
            SmartDashboard.putNumber("elbow ka", (voltage - (kV*elbow.getVelocity()))/accel);
            lastVelocity = elbow.getVelocity();
        }
        super.execute();
        SmartDashboard.putNumber("elbow velocity", elbow.getVelocity());
        double ksAndKg = Math.signum(voltage) * RobotMap.TelescopicArm.Elbow.kS + Elbow.getStaticFeedForward(Extender.getInstance().getLength(), elbow.getAngleRadians());
        elbow.setMotorVoltage(voltage + ksAndKg);
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || elbow.isAtAngle(voltage > 0 ? RobotMap.TelescopicArm.Elbow.FORWARD_ANGLE_LIMIT : RobotMap.TelescopicArm.Elbow.BACKWARD_ANGLE_LIMIT);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);

        elbow.setMotorVoltage(0);
        SmartDashboard.putNumber("extender kv", (Math.abs(voltage)) / Math.abs(elbow.getVelocity()));
    }

}
