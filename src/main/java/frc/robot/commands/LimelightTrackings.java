package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.swervedrive.drivebase.TeleopDrive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;


public class LimelightTrackings extends Command {

  // track with the front limelight
  public static Command getLimelightTrackingFrontNoTimeout(SwerveSubsystem drivebase, LimeLight limelight, double x_offset) {
    return new TeleopDrive(
      drivebase, 

      ()-> Math.copySign(
        Math.min(
          // its okay, the environments fine
          Math.abs((limelight.DDDx3_data3D[2]-0.1)), 
          0.8
        ), 
        limelight.DDDx3_data3D[2]
      ),  // this is y
          
      ()->Math.copySign( 
        Math.min(
          Math.abs((limelight.DDDx3_data3D[0]-x_offset)*(10)),
          0.8
        ), 
        limelight.DDDx3_data3D[0]-x_offset
      ), // this is x 

      ()-> Math.copySign(
        Math.min(
          Math.abs(limelight.DDDx3_data3D[4]*10), 
          0.3
        ), // this is yaw
        -limelight.DDDx3_data3D[4]
      ),

      ()-> false// this is robot-centered
    );
  }

  public static Command getLimelightTrackingFront(SwerveSubsystem drivebase, LimeLight limelight, double x_offset) {
    return new ParallelRaceGroup(
      new WaitCommand(5),
      getLimelightTrackingFrontNoTimeout(drivebase, limelight, x_offset)
    );
  }

  // track with the back limelight
  public static Command getLimelightTrackingBack(SwerveSubsystem drivebase, LimeLight limelight) {
    return new ParallelRaceGroup(
      new WaitCommand(4),
      new TeleopDrive(
        drivebase, 
        ()-> Math.copySign(
          Math.min(
            Math.abs((limelight.DDDx3_data3D[2]-0.1)), 
            0.8
          ), 
          -limelight.DDDx3_data3D[2]
          ), // this is y
            
          ()->Math.copySign( 
              Math.min(
                Math.abs((limelight.DDDx3_data3D[0])*(10)),
                0.8
              ), 
              -limelight.DDDx3_data3D[0]
            ), // this is x

          ()-> Math.copySign(
            Math.min(
              Math.abs(limelight.DDDx3_data3D[4]*10), 
              0.3
            ), 
            limelight.DDDx3_data3D[4]
          ), // this is yaw

          ()-> false // this is robot-centered
      )
    );
  }
    
}