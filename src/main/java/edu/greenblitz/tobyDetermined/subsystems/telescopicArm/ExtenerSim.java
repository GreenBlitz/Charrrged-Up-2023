package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.greenblitz.tobyDetermined.Robot;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.StayAtCurrentAngle;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.StayAtCurrentLength;

public class ExtenerSim extends GBSubsystem {


    private static ExtenderState state = ExtenderState.IN_ROBOT_BELLY_LENGTH;

    private ProfiledPIDController profiledPIDController;
    private static ExtenerSim instance;
    private GBSparkMax motor;

    //mechanisems values
    private final ElevatorSim sim;
    private final double drumRadiusMeters = 0.3;
    private final double minHeightMeters = 0;
    private final double maxHeightMeters = 2;
    private final double carriageMassKg = 2;
    private Encoder encoder;
    private final EncoderSim encoder_sim;
    private final PIDController pid;
    private final PWMSparkMax motor_controller;
    private final double elevatorEncoderDistPerPulse;

    //visualisation values
    private final Mechanism2d elevator2d;
    private final MechanismRoot2d m_mech2dRoot;
    private final MechanismLigament2d m_elevatorMech2d;
    

    public static ExtenerSim getInstance() {
        if (instance == null) {
            init();
            SmartDashboard.putBoolean("extender initialized via getinstance", true);
        }
        return instance;
    }

    public static void init(){
        instance = new ExtenerSim();
    }

    private ExtenerSim() {
        if(Robot.isReal()){
            motor = new GBSparkMax(RobotMap.telescopicArm.extender.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
            motor.getEncoder().setPosition(0); //maybe absolute encoder+*
            motor.config(new GBSparkMax.SparkMaxConfObject()
                    .withPID(RobotMap.telescopicArm.extender.PID)
                    .withPositionConversionFactor(RobotMap.telescopicArm.extender.CONVERSION_FACTOR)
                    .withIdleMode(CANSparkMax.IdleMode.kBrake)
                    .withRampRate(RobotMap.telescopicArm.extender.RAMP_RATE)
                    .withCurrentLimit(RobotMap.telescopicArm.extender.CURRENT_LIMIT)
                    .withVoltageComp(RobotMap.General.VOLTAGE_COMP_VAL)
            );
            motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, RobotMap.telescopicArm.extender.BACKWARDS_LIMIT);
            motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.telescopicArm.extender.FORWARD_LIMIT);
    
    
            profiledPIDController = new ProfiledPIDController(
                    RobotMap.telescopicArm.extender.PID.getKp(),
                    RobotMap.telescopicArm.extender.PID.getKi(),
                    RobotMap.telescopicArm.extender.PID.getKd(),
                    RobotMap.telescopicArm.extender.CONSTRAINTS
            );
        }
        
        sim = new ElevatorSim(DCMotor.getNEO(2),50/1, carriageMassKg, drumRadiusMeters, minHeightMeters, maxHeightMeters,false);
        encoder = new Encoder(2,3);
        encoder_sim = new EncoderSim(encoder);
        motor_controller = new PWMSparkMax(2);
        pid = new PIDController(5, 0, 0.5);
        pid.setTolerance(0.05);
        pid.setSetpoint(2.8);
        elevatorEncoderDistPerPulse = 2 * Math.PI * drumRadiusMeters / 4096;
        encoder.setDistancePerPulse(elevatorEncoderDistPerPulse);

    
        elevator2d = new Mechanism2d(20, 50);
        m_mech2dRoot = elevator2d.getRoot("Elevator Root", 10, 0);
        m_elevatorMech2d = m_mech2dRoot.append(new MechanismLigament2d("Elevator",sim.getPositionMeters(),90));
        SmartDashboard.putData("Mech2d", elevator2d);




    }

    @Override
    public void periodic() {
        state = getHypotheticalState(getLength());
        SmartDashboard.putString("ex state", state.toString());


    }

    public static ExtenderState getHypotheticalState(double lengthInMeters) {

        if (lengthInMeters > RobotMap.telescopicArm.extender.FORWARD_LIMIT || lengthInMeters < RobotMap.telescopicArm.extender.BACKWARDS_LIMIT) {
            return ExtenderState.OUT_OF_BOUNDS;
        }else if (lengthInMeters < RobotMap.telescopicArm.extender.MAX_ENTRANCE_LENGTH){
            return ExtenderState.IN_WALL_LENGTH;
        } else if (lengthInMeters < RobotMap.telescopicArm.extender.MAX_LENGTH_IN_ROBOT) {
            return ExtenderState.IN_ROBOT_BELLY_LENGTH;
        } else {
            return ExtenderState.OPEN;
        }
    }


    public static double getFeedForward(double wantedSpeed, double wantedAcceleration, double elbowAngle) {
        return getStaticFeedForward(elbowAngle) +
                RobotMap.telescopicArm.extender.kS * Math.signum(wantedSpeed) +
                RobotMap.telescopicArm.extender.kV * wantedSpeed +
                RobotMap.telescopicArm.extender.kA * wantedAcceleration;
    }

    public static double getStaticFeedForward (double elbowAngle){
        return Math.sin(elbowAngle - RobotMap.telescopicArm.elbow.STARTING_ANGLE_RELATIVE_TO_GROUND) * RobotMap.telescopicArm.extender.kG ;
    }

    private void setLengthByPID(double lengthInMeters) {;
        if(Robot.isReal()){
            profiledPIDController.reset(getLength());
            profiledPIDController.setGoal(lengthInMeters);
            double feedForward = getFeedForward(
                    profiledPIDController.getSetpoint().velocity, (profiledPIDController.getSetpoint().velocity - motor.getEncoder().getVelocity()) / RoborioUtils.getCurrentRoborioCycle(),ElbowSim.getInstance().getAngle());
            motor.getPIDController().setReference(profiledPIDController.getSetpoint().velocity, CANSparkMax.ControlType.kVelocity, 0, feedForward);
    
        }
        else{
            pid.setSetpoint(lengthInMeters);
            double output = pid.calculate(getLength());
            setSimMotorVolt(output);
        }
    }

    public void moveTowardsLength(double lengthInMeters) {
        SmartDashboard.putNumber("desired Len", lengthInMeters);
        // going out of bounds should not be allowed
        if (getHypotheticalState(lengthInMeters) == ExtenderState.OUT_OF_BOUNDS) {
            stop();
            return;
        }
        // arm should not extend to open state when inside the belly (would hit chassis)
        else if (ElbowSim.getInstance().getState() == ElbowSim.ElbowState.IN_BELLY && getHypotheticalState(lengthInMeters) == ExtenderState.OPEN) {
            SmartDashboard.putString("Alert", "Trying to open in Belly");
            setLengthByPID(RobotMap.telescopicArm.extender.MAX_ENTRANCE_LENGTH);
        }
        // arm should not extend too much in front of the wall
        else if (ElbowSim.getInstance().getState() == ElbowSim.ElbowState.WALL_ZONE && getHypotheticalState(lengthInMeters) != ExtenderState.IN_WALL_LENGTH){
            SmartDashboard.putString("Alert", "IN WALL AND TRYING TO OPEN OVER");
            stop();

        }
        else{
            SmartDashboard.putString("Alert", "");
            setLengthByPID(lengthInMeters);
        }
    }

    private void setSimMotorVolt(double output){
        this.motor_controller.setVoltage(output);
        //SmartDashboard.putNumber("voltage", output);

    }

    public double getLength() {
        if(Robot.isReal()){
            return motor.getEncoder().getPosition();

        }
        else{
            return sim.getPositionMeters();
        }
    }

    public ExtenderState getState() {
        return state;
    }

    public void stop() {
        if(Robot.isReal()){
            motor.set(0);
        }
        else{
            setSimMotorVolt(0);
        }
    }

    public enum ExtenderState {
        //see elbow state first
        //the state corresponding to IN_BELLY
        IN_ROBOT_BELLY_LENGTH,
        //the state corresponding to OUT_ROBOT
        OPEN,
        //the state corresponding to WALL_ZONE
        IN_WALL_LENGTH,
        // this state should not be possible and is either used to stop dangerous movement or to signal a bug
        OUT_OF_BOUNDS
    }

    public boolean isAtLength(double wantedLengthInMeters) {
        return Math.abs(getLength() - wantedLengthInMeters) <= RobotMap.telescopicArm.extender.LENGTH_TOLERANCE;
    }

    public void setMotorVoltage (double voltage){
        motor_controller.setVoltage(voltage);
    }

      /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
    setDefaultCommand(new StayAtCurrentLength());

    SmartDashboard.putNumber("elevtor height", this.getLength());
    m_elevatorMech2d.setLength(this.sim.getPositionMeters());
    // In this method, we update our simulation of what our elevator is doing
    // First, we set our "inputs" (voltages)
    this.sim.setInput(this.motor_controller.get() * 12);
    // Next, we update it. The standard loop time is 20ms.
    this.sim.update(0.020);
    // Finally, we set our simulated encoder's readings and simulated battery
    // voltage
    this.encoder_sim.setDistance(this.sim.getPositionMeters());
    // SimBattery estimates loaded battery voltages
    this.m_elevatorMech2d.setLength(Units.metersToInches(this.sim.getPositionMeters()));
    SmartDashboard.putNumber("Extender len", this.getLength());
    //System.out.println("Extender");
    //System.out.println(this.getLength());
    //System.out.println(getHypotheticalState(this.getLength()));

  }
}



