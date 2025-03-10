package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.swervedrive.drivebase.TeleopDrive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;


public class LimelightTracking extends Command {

  // track with the front limelight
  public static Command Front(SwerveSubsystem drivebase, LimeLight limelight, RobotContainer robot_container) {
    return new TeleopDrive(
      drivebase, 

      ()-> Math.copySign(
        Math.min(
          // its okay, the environments fine
          Math.abs((limelight.DDDx3_data3D[2]-0.1)), 
          0.8
        ), 
        limelight.DDDx3_data3D[2]
      )*limelight.tv,  // this is y
          
      ()->Math.copySign( 
        Math.min(
          Math.abs((limelight.DDDx3_data3D[0]-robot_container.x_offset)*(10)),
          0.8
        ), 
        limelight.DDDx3_data3D[0]-robot_container.x_offset
      )*limelight.tv, // this is x 

      ()-> Math.copySign(
        Math.min(
          Math.abs(limelight.DDDx3_data3D[4]*10), 
          0.3
        ), // this is yaw
        -limelight.DDDx3_data3D[4]
      )*limelight.tv,

      ()-> false// this is robot-centered
    );
  }

  // track with the back limelight
  public static Command Back(SwerveSubsystem drivebase, LimeLight limelight) {
    return new TeleopDrive(
      drivebase, 

      ()-> Math.copySign(
        Math.min(
          Math.abs((limelight.DDDx3_data3D[2]-0.1)), 
          0.8
        ), 
        -limelight.DDDx3_data3D[2]
        )*limelight.tv, // this is y
          
        ()->Math.copySign( 
            Math.min(
              Math.abs((limelight.DDDx3_data3D[0])*(10)),
              0.8
            ), 
            limelight.DDDx3_data3D[0]
          )*limelight.tv, // this is x

        ()-> Math.copySign(
          Math.min(
            Math.abs(limelight.DDDx3_data3D[4]*10), 
            0.3
          ), 
          limelight.DDDx3_data3D[4]
        )*limelight.tv, // this is yaw

        ()-> false // this is robot-centered
    );
  }
    
}