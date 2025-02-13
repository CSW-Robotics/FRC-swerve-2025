package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;


public class Cmd_LimeLightTracking extends Command {

    private final LimeLight m_LimeLight;
    private final SwerveSubsystem m_drivebase;

    public Cmd_LimeLightTracking(SwerveSubsystem drivebase, LimeLight subsystem) {

        m_LimeLight = subsystem;
        m_drivebase = drivebase;
        addRequirements(m_LimeLight, m_drivebase);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // make the drivetrain command
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // update that command
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        // stop drivetran command
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
        // if we are close, kill THIS command
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}