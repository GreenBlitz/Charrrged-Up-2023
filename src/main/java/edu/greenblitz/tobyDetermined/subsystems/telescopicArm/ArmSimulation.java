package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import org.littletonrobotics.junction.Logger;

public class ArmSimulation extends GBSubsystem {


    public static final double ARM_LENGTH_ADD = 0.5;
    public static final double LINE_WIDTH = 10;
    public static final double ANGLE = 0;
    static final Mechanism2d ARM_MECHANISM = new Mechanism2d(
            RobotMap.TelescopicArm.Extender.FORWARD_LIMIT * 2,
            RobotMap.TelescopicArm.Extender.FORWARD_LIMIT,
            new Color8Bit(Color.kBlack));
    private static final MechanismRoot2d ARM_MECHANISM_ROOT = ARM_MECHANISM.getRoot("ArmRoot", RobotMap.TelescopicArm.Extender.FORWARD_LIMIT, ARM_LENGTH_ADD);
    static final MechanismLigament2d
            ARM_LIGAMENT = ARM_MECHANISM_ROOT.append(new MechanismLigament2d(
                    "zShowfirst armLigament",
            RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT + ARM_LENGTH_ADD,
            ANGLE,
            LINE_WIDTH,
            new Color8Bit(Color.kBlue)));

    public static void init() {
        new ArmSimulation();
    }

    @Override
    public void periodic() {

        ARM_LIGAMENT.setLength((Extender.getInstance().getLength()) + ARM_LENGTH_ADD);
        ARM_LIGAMENT.setAngle(Units.radiansToDegrees(Elbow.getInstance().getAngleRadians()));


        Logger.getInstance().recordOutput("Arm/ArmMechanism", ARM_MECHANISM);

        Logger.getInstance().recordOutput("Arm/SimPose3D", getArmPosition(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians()));
        Logger.getInstance().recordOutput("Arm/TargetPose3D", getArmPosition(Extender.getInstance().getGoalLength(), Elbow.getInstance().getGoalAngle()));



    }

    public static Pose3d getArmPosition(double extenderLength, double elbowAngle) {

        Pose3d armToRobotPose = new Pose3d(
                RobotMap.SimulationConstants.ARM_TO_ROBOT,
                new Rotation3d(0, -elbowAngle, 0)
        );
        return armToRobotPose.transformBy(
                new Transform3d(
                new Translation3d(
                        0,
                        0,
                        -extenderLength + RobotMap.TelescopicArm.Extender.EXTENDED_LENGTH
                        ),
                        new Rotation3d()
        ));
    }
}
