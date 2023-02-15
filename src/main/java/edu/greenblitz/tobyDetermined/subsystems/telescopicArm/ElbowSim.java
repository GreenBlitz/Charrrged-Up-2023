package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.greenblitz.tobyDetermined.Robot;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElbowSim extends GBSubsystem {

    private static ElbowSim instance;
    public ElbowState state = ElbowState.IN_BELLY;
    public GBSparkMax motor;
    private double lastSpeed;

    private static final int kMotorPort = 19;
    private static final int kEncoderAChannel = 0;
    private static final int kEncoderBChannel = 1;
    private static final int kJoystickPort = 0;

    public static final String kArmPositionKey = "ArmPosition";
    public static final String kArmPKey = "ArmP";
    // The P gain for the PID controller that drives this arm.

    private static double armPositionDeg = 2.0;

    // distance per pulse = (angle per revolution) / (pulses per revolution)
    // = (2 * PI rads) / (4096 pulses)
    private static final double kArmEncoderDistPerPulse = 2.0 * Math.PI / 4096;

    // The arm gearbox represents a gearbox containing two Vex 775pro motors.
    private final DCMotor m_armGearbox = DCMotor.getVex775Pro(2);

    // Standard classes for controlling our arm
    private final PIDController m_controller = new PIDController(5, 1, 0);;
    private final Encoder m_encoder = new Encoder(kEncoderAChannel, kEncoderBChannel);
    private final PWMSparkMax m_motor = new PWMSparkMax(kMotorPort);

    // Simulation classes help us simulate what's going on, including gravity.
    private static final double angle_tolerance = 0;
    private static final double m_armReduction = 2000;
    private static final double m_armMass = 1.0; // Kilograms
    private static final double m_armLength = Units.inchesToMeters(30);
    // This arm sim represents an arm that can travel from -75 degrees (rotated down
    // front)
    // to 255 degrees (rotated down in the back).

    // Create a Mechanism2d display of an Arm with a fixed ArmTower and moving Arm.
    private final Mechanism2d m_mech2d = new Mechanism2d(60, 60);
    private final MechanismRoot2d m_armPivot = m_mech2d.getRoot("ArmPivot", 30, 30);
    private final MechanismLigament2d m_armTower = m_armPivot.append(new MechanismLigament2d("ArmTower", 30, 70));
    private static SingleJointedArmSim m_armSim;

    private MechanismLigament2d m_arm;

    public static ElbowSim getInstance() {
        if (instance == null) {
            init();
            SmartDashboard.putBoolean("elbow initialized via getinstance", true);
        }
        return instance;
    }

    public static void init() {
        instance = new ElbowSim();
    }

    private ElbowSim() {
        if (Robot.isReal()) {

            motor = new GBSparkMax(RobotMap.telescopicArm.elbow.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

            motor.config(new GBSparkMax.SparkMaxConfObject()
                    .withPID(RobotMap.telescopicArm.elbow.PID)
                    .withPositionConversionFactor(RobotMap.telescopicArm.elbow.CONVERSION_FACTOR)
                    .withIdleMode(CANSparkMax.IdleMode.kBrake)
                    .withRampRate(RobotMap.telescopicArm.elbow.MOTOR_RAMP_RATE)
                    .withCurrentLimit(RobotMap.telescopicArm.elbow.CURRENT_LIMIT)
                    .withVoltageComp(RobotMap.General.VOLTAGE_COMP_VAL));
            motor.getEncoder().setPosition(0); // resetPosition
            motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse,
                    RobotMap.telescopicArm.elbow.BACKWARD_ANGLE_LIMIT);
            motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward,
                    RobotMap.telescopicArm.elbow.FORWARD_ANGLE_LIMIT);

            lastSpeed = 0;
        } else {
            m_armSim = new SingleJointedArmSim(
                    m_armGearbox,
                    m_armReduction,
                    SingleJointedArmSim.estimateMOI(m_armLength, m_armMass),
                    m_armLength,
                    Units.degreesToRadians(0 + angle_tolerance),
                    Units.degreesToRadians(270 + angle_tolerance),
                    false);
            m_arm = m_armPivot.append(
                    new MechanismLigament2d(
                            "Arm",
                            30,
                            Units.radiansToDegrees(Units.degreesToRadians(0)),
                            6,
                            new Color8Bit(Color.kYellow)));
            m_encoder.setDistancePerPulse(kArmEncoderDistPerPulse);
            SmartDashboard.putData("Mech2d2", m_mech2d);
            m_controller.setTolerance(Units.degreesToRadians(10));

        }
    }

    public void moveTowardsAngle(double angleInRads) {
        // going out of bounds should not be allowed
        if (ElbowSim.getHypotheticalState(angleInRads) == ElbowState.OUT_OF_BOUNDS) {

            stop();
            return;
        }

        // when moving between states the arm always passes through the
        // IN_FRONT_OF_ENTRANCE zone and so length must be short enough
        // if its not short enough the arm will approach the start of the zone
        else if (getState() != getHypotheticalState(angleInRads) &&
                ExtenerSim.getInstance().getState() != ExtenerSim.ExtenderState.IN_WALL_LENGTH) {


            setAngleByPID(state == ElbowState.IN_BELLY ? RobotMap.telescopicArm.elbow.STARTING_WALL_ZONE_ANGLE
                    : RobotMap.telescopicArm.elbow.END_WALL_ZONE_ANGLE);
        } else {


            setAngleByPID(angleInRads);
        }
    }

    public void setAngleByPID(double goalAngle) {
        if (Robot.isReal()) {

            ProfiledPIDController pidController = RobotMap.telescopicArm.elbow.PID_CONTROLLER;
            pidController.reset(getAngle());
            pidController.setGoal(goalAngle);
            double feedForward = getFeedForward(
                    pidController.getSetpoint().velocity,
                    (pidController.getSetpoint().velocity - lastSpeed) / RoborioUtils.getCurrentRoborioCycle(),
                    ExtenerSim.getInstance().getLength(), getAngle());
            motor.getPIDController().setReference(pidController.getSetpoint().velocity,
                    CANSparkMax.ControlType.kVelocity,
                    0, feedForward);
        } else {
            m_controller.setSetpoint(goalAngle);
            double output = m_controller.calculate(Units.degreesToRadians(this.getAngle()));
            setMotorVoltage(output);

        }
    }

    public double getAngle() {
        return Math.toDegrees(m_armSim.getAngleRads());
    }

    public ElbowState getState() {
        return state;
    }

    public void stop() {
        m_motor.set(0);
    }

    @Override
    public void periodic() {

        state = getHypotheticalState(Units.degreesToRadians(getAngle()));
        SmartDashboard.putString("elbow state", state.toString());
    }

    public static ElbowState getHypotheticalState(double angleInRads) {
        //System.out.println(Units.radiansToDegrees(angleInRads));
        if (angleInRads > RobotMap.telescopicArm.elbow.FORWARD_ANGLE_LIMIT
                || angleInRads < RobotMap.telescopicArm.elbow.BACKWARD_ANGLE_LIMIT) {
            return ElbowState.OUT_OF_BOUNDS;
        } else if (angleInRads > RobotMap.telescopicArm.elbow.STARTING_WALL_ZONE_ANGLE
                && angleInRads < RobotMap.telescopicArm.elbow.END_WALL_ZONE_ANGLE) {
            return ElbowState.WALL_ZONE;
        } else if (angleInRads > RobotMap.telescopicArm.elbow.END_WALL_ZONE_ANGLE) {
            return ElbowState.OUT_ROBOT;
        }
        return ElbowState.IN_BELLY;

    }

    public boolean isAtAngle(double wantedAngle) {
        return Math.abs(getAngle() - wantedAngle) <= RobotMap.telescopicArm.elbow.ANGLE_TOLERANCE;
    }

    public boolean isInTheSameState(double wantedAng) {
        return getHypotheticalState(getAngle()) == getHypotheticalState(wantedAng)
                && getHypotheticalState(wantedAng) != ElbowState.OUT_OF_BOUNDS;
    }

    public static double getFeedForward(double wantedAngularSpeed, double wantedAcc, double extenderLength,
            double elbowAngle) {
        double Kg = getStaticFeedForward(extenderLength, elbowAngle);
        return Kg + RobotMap.telescopicArm.elbow.kS * Math.signum(wantedAngularSpeed)
                + RobotMap.telescopicArm.elbow.kV * wantedAngularSpeed + RobotMap.telescopicArm.elbow.kA * wantedAcc;
    }

    public static double getStaticFeedForward(double extenderLength, double elbowAngle) {
        return (RobotMap.telescopicArm.elbow.MIN_Kg
                + (((RobotMap.telescopicArm.elbow.MAX_Kg - RobotMap.telescopicArm.elbow.MIN_Kg) * extenderLength)
                        / RobotMap.telescopicArm.extender.EXTENDED_LENGTH))
                * Math.cos(elbowAngle - RobotMap.telescopicArm.elbow.STARTING_ANGLE_RELATIVE_TO_GROUND);
    }

    /*
     * each elbow state represents some range of angles with a corresponding max
     * length represented by an extender state
     */

    public enum ElbowState {
        // the state of being inside the robot in front of the rotating plate
        IN_BELLY,
        // the state of being outside the robot for placement
        OUT_ROBOT,
        // the state of being in front of the plates wall, this should not be a
        // permanent state on purpose
        WALL_ZONE,
        // this state should not be possible and is either used to stop dangerous
        // movement or to signal a bug
        OUT_OF_BOUNDS
    }

    public void setMotorVoltage(double voltage) {
        m_motor.setVoltage(voltage);
    }

    @Override
    public void simulationPeriodic() {
        if (getState() == ElbowSim.ElbowState.WALL_ZONE &&
                ExtenerSim.getInstance().getState() == ExtenerSim.ExtenderState.OPEN)
            {
                System.out.println("*****************************BROKE***************************");
            } 
        // In this method, we update our simulation of what our arm is doing
        // First, we set our "inputs" (voltages)
        m_armSim.setInput(m_motor.get() * 12);

        // Next, we update it. The standard loop time is 20ms.
        m_armSim.update(0.020);

        // Finally, we set our simulated encoder's readings and simulated battery
        // voltage
        m_encoder.setDistancePerPulse(m_armSim.getAngleRads() + 1);
        // Update the Mechanism Arm angle based on the simulated arm angle
        m_arm.setAngle(Units.radiansToDegrees(m_armSim.getAngleRads()));

        SmartDashboard.putNumber("Elbow angle", this.getAngle());
        // System.out.println("Elbow");
        // System.out.println(getHypotheticalState(Units.degreesToRadians(this.getAngle())));
        // System.out.println(this.getAngle());

    }

}
