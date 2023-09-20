package edu.greenblitz.utils.motors;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenixpro.configs.MotionMagicConfigs;
import com.ctre.phoenixpro.configs.TalonFXConfiguration;
import com.ctre.phoenixpro.hardware.TalonFX;
import com.ctre.phoenixpro.signals.InvertedValue;
import com.ctre.phoenixpro.signals.NeutralModeValue;
import edu.greenblitz.utils.PIDObject;

public class GBFalcon extends TalonFX {
	
	/**
	 * Constructor
	 *
	 * @param deviceNumber [0,62]
	 */
	public GBFalcon(int deviceNumber) {
		super(deviceNumber);
	}
	
	/**
	 * configs the motor settings using FalconConfObject
	 *
	 * @param conf configObject, uses builder
	 */
	public void config(GBFalcon.FalconConfObject conf) {

		TalonFXConfiguration config = new TalonFXConfiguration();
		config.CurrentLimits.SupplyCurrentLimitEnable = true;
		config.CurrentLimits.SupplyCurrentLimit = conf.currentLimit;

		config.ClosedLoopRamps.VoltageClosedLoopRampPeriod = conf.getVoltageClosedLoopRamp();
		config.ClosedLoopRamps.TorqueClosedLoopRampPeriod = conf.currentLimit;

		config.OpenLoopRamps.TorqueOpenLoopRampPeriod = conf.ClosedLoopRamp;
		config.OpenLoopRamps.VoltageOpenLoopRampPeriod = conf.getVoltageClosedLoopRamp();

		config.MotorOutput.Inverted = conf.isInverted() ? InvertedValue.CounterClockwise_Positive : InvertedValue.Clockwise_Positive;

		config.Feedback.RotorToSensorRatio = conf.getConversionFactor();

		config.MotorOutput.NeutralMode = conf.getNeutralMode() == NeutralMode.Brake ? NeutralModeValue.Brake : NeutralModeValue.Coast;
		configPID(conf.pidObject);
	}


	public void configMotionMagicConsts (MotionMagicConfigs configs){
		super.getConfigurator().refresh(configs);
	}


	public void configPID(PIDObject pidObject) {

		TalonFXConfiguration pidConf = new TalonFXConfiguration();
		pidConf.Slot0.kP =  pidObject.getKp();
		pidConf.Slot0.kI =  pidObject.getKi();
		pidConf.Slot0.kD =  pidObject.getKd();
		 //from now on it will be kS and kV  as the FeedForward
		pidConf.Slot0.kS = pidObject.getKs();
		pidConf.Slot0.kV = pidObject.getKv();

		//set max voltage
		pidConf.Voltage.PeakForwardVoltage = pidObject.maxVoltage();
		pidConf.Voltage.PeakReverseVoltage = pidObject.maxVoltage();

		super.getConfigurator().refresh(pidConf);

	}
	
	/**
	 * @see GBSparkMax.SparkMaxConfObject
	 */
	public static class FalconConfObject {
		private PIDObject pidObject = new PIDObject(0, 0, 0);
		private int currentLimit = 0;
		private double rampRate = 0;
		private boolean inverted = false;
		private double ConversionFactor = 1;
		private double voltageClosedLoopRamp = 0; //how much time in second from 0 to 12v
		private double ClosedLoopRamp = 0; //how much time in second from 0 to 300A
		private NeutralMode neutralMode = NeutralMode.Brake;
		
		public FalconConfObject(FalconConfObject other) {
			this.pidObject = new PIDObject(other.pidObject);
			this.currentLimit = other.currentLimit;
			this.rampRate = other.rampRate;
			this.inverted = other.inverted;
			this.neutralMode = other.neutralMode;
			this.voltageClosedLoopRamp = other.voltageClosedLoopRamp;
		}
		
		public FalconConfObject() {
		
		}
		
		public int getCurrentLimit() {
			return currentLimit;
		}
		
		public double getRampRate() {
			return rampRate;
		}
		
		public boolean isInverted() {
			return inverted;
		}
		
		public double getConversionFactor() {
			return ConversionFactor;
		}
		
		public double getVoltageClosedLoopRamp() {
			return voltageClosedLoopRamp;
		}
		
		public NeutralMode getNeutralMode() {
			return neutralMode;
		}

		public FalconConfObject withVoltageRamp(double voltageRamp){
			this.voltageClosedLoopRamp = voltageRamp;
			return this;
		}
		
		public FalconConfObject withNeutralMode(NeutralMode neutralMode) {
			this.neutralMode = neutralMode;
			return this;
		}
		
		public FalconConfObject withConversionFactor(double velocityConversionFactor) {
			this.ConversionFactor = velocityConversionFactor;
			return this;
		}
		
		public FalconConfObject withPID(PIDObject pidObject) {
			this.pidObject = pidObject;
			return this;
		}
		
		public FalconConfObject withCurrentLimit(int currentLimit) {
			this.currentLimit = currentLimit;
			return this;
		}
		
		public FalconConfObject withRampRate(double rampRate) {
			this.rampRate = rampRate;
			return this;
		}
		
		public FalconConfObject withInverted(Boolean inverted) {
			this.inverted = inverted;
			return this;
		}

		
		public PIDObject getPidObject() {
			return pidObject;
		}
		
		
	}
}
