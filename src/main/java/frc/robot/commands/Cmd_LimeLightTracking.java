package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.swervedrive.drivebase.TeleopDrive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;


public class Cmd_LimeLightTracking extends Command {

    private final LimeLight m_LimeLight;
    private final SwerveSubsystem m_drivebase;
    private Command drivebaseCommand;

    public Cmd_LimeLightTracking(SwerveSubsystem drivebase, LimeLight subsystem) {

        m_LimeLight = subsystem;
        m_drivebase = drivebase;
        addRequirements(m_LimeLight, m_drivebase);
    }
    

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        // make the drivetrain command

        Command drivebaseCommand = new TeleopDrive(
            m_drivebase, 
            // maximum x and y values are 25 so we divide by 25
            ()-> (0.03/25)*m_LimeLight.DDDx3_data3D[2], 
            ()-> (0.03/25)*m_LimeLight.DDDx3_data3D[0], 

            // this needs to be worked on because I dont know what the values the limelight will give
            ()-> (0.03/25)*m_LimeLight.DDDx3_data3D[4],
            ()-> true

        );

        CommandScheduler.getInstance().schedule(drivebaseCommand);
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

        CommandScheduler.getInstance().cancel(drivebaseCommand);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        if (m_LimeLight.DDDx3_data3D[2] <= 10) {

            return true;
        }

        
        else {
        return false;
        }
        // if we are close, kill THIS command
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}