package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngleRadians;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import java.util.function.BooleanSupplier;

public class SimpleGoToPosition extends ParallelCommandGroup {

    private double lengthInMeters, angleInRads;

    private final BooleanSupplier isFinished;

    protected SimpleGoToPosition(double lengthInMeters, double angleInRads) {
        this.lengthInMeters = lengthInMeters;
        this.angleInRads = angleInRads;
        isFinished = () -> {
            SmartDashboard.putBoolean("ext at len", Extender.getInstance().isAtLength(this.lengthInMeters));
//            SmartDashboard.putBoolean("ext at vel", Extender.getInstance().isNotMoving());
            SmartDashboard.putBoolean("elb at ang", Elbow.getInstance().isAtAngle(this.angleInRads));
//            SmartDashboard.putBoolean("elb at vel", Elbow.getInstance().isNotMoving());
    
    
            return Extender.getInstance().isAtLength(this.lengthInMeters)
//                && Extender.getInstance().\[]
//                isNotMoving()
                && Elbow.getInstance().isAtAngle(this.angleInRads);};
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
