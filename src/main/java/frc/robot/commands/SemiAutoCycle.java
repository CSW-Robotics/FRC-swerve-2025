package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.swervedrive.drivebase.TeleopDrive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Dropper;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;


public class SemiAutoCycle extends Command {

    public static Command ScoreCoral(SwerveSubsystem drivebase, LimeLight m_frontLimelight, Elevator m_Elevator, Dropper m_Dropper, RobotContainer m_RobotContainer){

        return new SequentialCommandGroup(
            new InstantCommand (()-> m_Elevator.ChangeTargetStage(2)),
            new ParallelRaceGroup(
              LimelightTracking.Front(drivebase, m_frontLimelight, m_RobotContainer),
            
              new SequentialCommandGroup(
                new DieOnDoneTracking(m_frontLimelight, 0.65),
                new InstantCommand (()-> m_Elevator.ChangeTargetStage(m_RobotContainer.semi_auto_el_level)),
                new DieOnElevatorLevel(m_Elevator, m_RobotContainer),
                new InstantCommand (()->m_Dropper.setMotor(0.4)),
                new WaitCommand(0.5),
                new InstantCommand (()->m_Dropper.setMotor(0.0)),
                new InstantCommand (()-> m_Elevator.ChangeTargetStage(0))
          )));
    }

    public static Command GetCoral(SwerveSubsystem drivebase, LimeLight m_backLimelight, Dropper m_Dropper){

        return new SequentialCommandGroup(
          new ParallelRaceGroup(
            LimelightTracking.Back(drivebase, m_backLimelight),
            
            new SequentialCommandGroup(
              //new DieOnDoneTracking(m_backLimelight, 0.9),
              new InstantCommand(()-> m_Dropper.setMotor(0.1)),
              new WaitCommand(0.1),
              new InstantCommand(()-> m_Dropper.restartAutoOutake()),
              new WaitCommand(0.1),
              new InstantCommand(()-> m_Dropper.intakeCoral(0.5)),
              new DieOnIntaken(m_Dropper)
            )
          ));

    }

}