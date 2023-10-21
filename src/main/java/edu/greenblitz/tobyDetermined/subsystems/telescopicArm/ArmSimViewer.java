package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import org.littletonrobotics.junction.Logger;

import java.security.cert.TrustAnchor;

public class ArmSimViewer extends GBSubsystem {
    static final Mechanism2d ARM_MECHANISM = new Mechanism2d(
            RobotMap.TelescopicArm.Extender.FORWARD_LIMIT * 2,
            RobotMap.TelescopicArm.Extender.FORWARD_LIMIT,
            new Color8Bit(Color.kBlack));
    private static final MechanismRoot2d ARM_MECHANISM_ROOT = ARM_MECHANISM.getRoot("ArmRoot", RobotMap.TelescopicArm.Extender.FORWARD_LIMIT, 0.2);
    static final MechanismLigament2d
            TARGET_ARM_LIGAMENT = ARM_MECHANISM_ROOT.append(new MechanismLigament2d("targetArmLigament", RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT + 0.1, 0, 10, new Color8Bit(Color.kGray))),
            ARM_LIGAMENT = ARM_MECHANISM_ROOT.append(new MechanismLigament2d("zShowfirst armLigament", RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT + 0.1, 0, 10, new Color8Bit(Color.kBlue)));


    static TalonFX f = new TalonFX(30);






    public static void init() {
        new ArmSimViewer();
    }

    @Override
    public void periodic() {

        TARGET_ARM_LIGAMENT.setLength((Extender.getInstance().getGoalLength()) + 0.2);
        TARGET_ARM_LIGAMENT.setAngle(Units.radiansToDegrees(Elbow.getInstance().goalAngle));
        ARM_LIGAMENT.setLength((Extender.getInstance().getLength()) + 0.2);
        ARM_LIGAMENT.setAngle(Units.radiansToDegrees(Elbow.getInstance().getAngleRadians()));

//        SmartDashboard.putNumber("TalonFXPos", f.getSelectedSensorPosition());
//        SmartDashboard.putNumber("TalonFXVel", f.getSelectedSensorVelocity());
//        f.getSimCollection().setIntegratedSensorVelocity(100);


        Logger.getInstance().recordOutput("Arm/ArmMechanism", ARM_MECHANISM);
    }
}
