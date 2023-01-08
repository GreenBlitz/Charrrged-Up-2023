package edu.greenblitz.pegasus.commands.shooter;


import com.revrobotics.CANSparkMax;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.*;

public class DoubleShoot extends SequentialCommandGroup {

	private final double RPM1;
	private final double RPM2;

	public DoubleShoot(double RPM1, double RPM2) {
		super();
		this.RPM1 = RPM1;
		this.RPM2 = RPM2;
		System.out.println("Command Activated");
		addCommands(
				new ParallelRaceGroup(
						new InsertIntoShooter(),
						new ShooterByRPM(RPM1, 10),
						new SequentialCommandGroup(
								new WaitUntilCommand(() -> Shooter.getInstance().isPreparedToShoot()),
								new WaitCommand(0.5) //if doesnt shoot second ball sometimes set to 1
						)
				),
				new ParallelDeadlineGroup(
						new WaitCommand(0.5),
						new MoveBallUntilClick()
				),
				new ParallelRaceGroup(
						new InsertIntoShooter(),
						new ShooterByRPM(RPM2, 10),
						new SequentialCommandGroup(
								new WaitUntilCommand(() -> Shooter.getInstance().isPreparedToShoot()),
								new WaitCommand(1)
						)),
				new WaitCommand(0.2)

		);

	}

	public DoubleShoot(double RPM) {
		this(RPM, RPM);
	}

	public DoubleShoot() {
		this(edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter.ShooterMotor.RPM);
	}

	@Override
	public void initialize() {
		Shooter.getInstance().setIdleMode(CANSparkMax.IdleMode.kCoast);
		super.initialize();
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		Shooter.getInstance().setSpeedByPID(0);
	}
}
