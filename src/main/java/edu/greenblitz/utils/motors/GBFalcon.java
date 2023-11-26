package edu.greenblitz.utils.motors;
import com.ctre.phoenix6.configs.*;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
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
		var motorConfigure = new TalonFXConfiguration();
		super.getConfigurator().apply(new TalonFXConfiguration());
		motorConfigure.withCurrentLimits(new CurrentLimitsConfigs().withStatorCurrentLimit(conf.getCurrentLimit()).withSupplyCurrentLimit(conf.getCurrentLimit()).withSupplyCurrentLimitEnable(true).withStatorCurrentLimitEnable(true));
		motorConfigure.withOpenLoopRamps(new OpenLoopRampsConfigs().withDutyCycleOpenLoopRampPeriod(conf.getRampRate()));
		super.setInverted(conf.isInverted());
//		super.configSelectedFeedbackCoefficient(conf.getConversionFactor());
//		super.configVoltageCompSaturation(conf.getVoltageCompSaturation());
		super.setNeutralMode(conf.getNeutralMode());
		configPID(conf.pidObject);
	}
	
	public void configPID(PIDObject pidObject) {
//		super.config_kF(0, pidObject.getKf());
//		super.config_IntegralZone(0, pidObject.getIZone());
//		super.configClosedLoopPeakOutput(0, pidObject.getMaxPower());
		var pidConfigure = new Slot0Configs();
		pidConfigure.kP = pidObject.getKp();
		pidConfigure.kI = pidObject.getKi();
		pidConfigure.kD = pidObject.getKd();
		super.getConfigurator().apply(pidConfigure, 0.050);
	}
	

	public static class FalconConfObject {
		private PIDObject pidObject = new PIDObject(0, 0, 0);
		private int currentLimit = 0;
		private double rampRate = 0;
		private boolean inverted = false;
		private double conversionFactor = 1;
		private double voltageCompSaturation = 0;
		private NeutralModeValue neutralMode = NeutralModeValue.Brake;
		
		public FalconConfObject(FalconConfObject other) {
			this.pidObject = new PIDObject(other.pidObject);
			this.currentLimit = other.currentLimit;
			this.rampRate = other.rampRate;
			this.inverted = other.inverted;
			this.neutralMode = other.neutralMode;
			this.voltageCompSaturation = other.voltageCompSaturation;
			this.conversionFactor = other.conversionFactor;
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
			return conversionFactor;
		}
		
		public double getVoltageCompSaturation() {
			return voltageCompSaturation;
		}
		
		public NeutralModeValue getNeutralMode() {
			return neutralMode;
		}
		
		public FalconConfObject withNeutralMode(NeutralModeValue neutralMode) {
			this.neutralMode = neutralMode;
			return this;
		}
		
		public FalconConfObject withConversionFactor(double velocityConversionFactor) {
			this.conversionFactor = velocityConversionFactor;
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


		
		
		public FalconConfObject withVoltageCompSaturation(double voltageCompSaturation) {
			this.voltageCompSaturation = voltageCompSaturation;
			return this;
		}
		
		public PIDObject getPidObject() {
			return pidObject;
		}
		
		
	}
}
