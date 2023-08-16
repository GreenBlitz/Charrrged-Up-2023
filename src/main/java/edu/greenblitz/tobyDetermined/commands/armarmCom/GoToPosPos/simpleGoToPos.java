package edu.greenblitz.tobyDetermined.commands.armarmCom.GoToPosPos;

import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngleRadians;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import java.util.function.BooleanSupplier;

public class simpleGoToPos extends ParallelCommandGroup {

    private double lengthInMeters, angleInRads;

    private final BooleanSupplier isFinished;

    protected simpleGoToPos(double lengthInMeters, double angleInRads) {
        this.lengthInMeters = lengthInMeters;
        this.angleInRads = angleInRads;
        isFinished = () -> {
            SmartDashboard.putBoolean("ext at len", Extender.getInstance().isAtLength(this.lengthInMeters));
//            SmartDashboard.putBoolean("ext at vel", Extender.getInstance().isNotMoving());
            SmartDashboard.putBoolean("elb at ang", ElbowSub.getInstance().isAtAngle(this.angleInRads));
//            SmartDashboard.putBoolean("elb at vel", Elbow.getInstance().isNotMoving());


            return Extender.getInstance().isAtLength(this.lengthInMeters)
//                && Extender.getInstance().\[]
//                isNotMoving()
                    && ElbowSub.getInstance().isAtAngle(this.angleInRads);};
//                && Elbow.getInstance().isNotMoving();};
        addCommands(
                new ExtendToLength(lengthInMeters){
                    @Override
                    public boolean isFinished() {
                        return false;
                    }
                }.until(isFinished),
                new RotateToAngleRadians(angleInRads){
                    @Override
                    public boolean isFinished() {
                        return false;
                    }
                }.until(isFinished));
    }
}