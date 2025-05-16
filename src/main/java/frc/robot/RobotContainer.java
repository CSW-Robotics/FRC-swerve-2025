// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


// Never Forget: ransomware, siblings, Big "P", limelight chandelier, 60 factorial, greenery, and sound effects 
package frc.robot;

import com.ctre.phoenix6.signals.Led1OffColorValue;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DieOnDoneTracking;
import frc.robot.commands.DieOnElevatorLevel;
import frc.robot.commands.LimelightTracking;
import frc.robot.commands.SemiAutoCycle;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDrive;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDriveAdv;
import frc.robot.commands.swervedrive.drivebase.TeleopDrive;
import frc.robot.subsystems.Dropper;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;

// our robot container object
public class RobotContainer
{
  // subsystems
  public final SwerveSubsystem drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve/neo"));
  private final LimeLight  m_frontLimelight = new LimeLight("limelight");
  private final LimeLight     m_backLimelight = new LimeLight("limelight-front");
  private final Elevator m_Elevator = new Elevator();
  private final Dropper m_Dropper = new Dropper();


  // controllers
  public XboxController m_XboxController = new XboxController(2);
  public Joystick drive_joystick = new Joystick(1);
  public Joystick angle_joystick = new Joystick(0);
  public XboxController BackupController = new XboxController(3);

  // auto picker
  private final SendableChooser<String> m_auto_chooser = new SendableChooser<>();
  
  // x offests of the auto tracking
  public double x_offset_left =  0.15;
  public double x_offset_right = -0.175;
  public double x_offset = x_offset_right;

  public int semi_auto_el_level = 1;

  public Field2d current_field = new Field2d();

  public Pose2d current_pos = new Pose2d();


  
 
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {





  // ##### FILE MANAGER FOR AUTO PICKER ON SD #####

    // get all the files in the pathplanner/autos dir
    File[] files_in_deploy_folder = new File(
      Filesystem.getDeployDirectory(),"pathplanner/autos").listFiles((dir, name) -> name.endsWith(".auto")
    );
    
    // and then add them to a list
    for (File i_file : files_in_deploy_folder) {
      if (i_file.isFile()) {
        m_auto_chooser.addOption( // add option to SmartDashboard
          i_file.getName().substring(0, i_file.getName().lastIndexOf(".")), // removed .auto
          i_file.getName().substring(0, i_file.getName().lastIndexOf("."))
        );
      }
    }
    // put it on SmartDashboard
    m_auto_chooser.setDefaultOption("Middle Start", "Middle Start");
    SmartDashboard.putData("Auto Chooser Mk4", m_auto_chooser);

  

    // set up buttons and commands
    configureBindings();
  }


  // deadband function to put on joysticks
  private double deadband(double input, double min) {
    if (Math.abs(input) < min) {
      return 0.0;
    } else {
      return input;
    }
  }


  private void configureBindings() {

  // ##### NAMED COMMANDS FOR PATHPLANNER #####

    // freeze the wheels
    NamedCommands.registerCommand("FreezeWheels", 
      new TeleopDrive(drivebase, ()->0.0, ()->0.0, ()->0.0, ()->true )
    );

    NamedCommands.registerCommand("TargetLeft", new InstantCommand(()-> this.x_offset = this.x_offset_left));

    NamedCommands.registerCommand("TargetLevel2", new InstantCommand(()-> this.semi_auto_el_level = 1));
    NamedCommands.registerCommand("TargetLevel4", new InstantCommand(()-> this.semi_auto_el_level = 3));

    // back limelight tracking
    NamedCommands.registerCommand("TrackBackAndIntake", 
    SemiAutoCycle.GetCoral(drivebase, m_backLimelight, m_Dropper)
    );

    // tracks to front, places anywhere on reef (not level 1)
    NamedCommands.registerCommand("TrackFrontAndScore", 
    new SequentialCommandGroup(
      SemiAutoCycle.ScoreCoral(drivebase, m_frontLimelight, m_Elevator, m_Dropper, this),
      new InstantCommand (()-> m_Elevator.ChangeTargetStage(0)))
    );





  // ##### SEMI-AUTOs FOR TELEOP #####

    // a button to start limelight tracking (from the front) [on driving joystick trigger]
    new JoystickButton(drive_joystick, 1).whileTrue(
      LimelightTracking.Front(drivebase, m_frontLimelight, this)
    );

    // a button to start limelight tracking (from the back) [on turing joystick trigger]
    new JoystickButton(angle_joystick, 1).whileTrue(
      LimelightTracking.Back(drivebase, m_backLimelight)
      //LimelightTracking.Front(drivebase, m_frontLimelight, this)
    );


    // Get coral from the coral station
    new JoystickButton(drive_joystick, 4).whileTrue(
      SemiAutoCycle.GetCoral(drivebase, m_backLimelight, m_Dropper)
    );

    // Score the coral we have in our robot
    new JoystickButton(drive_joystick, 3)
    .whileTrue(SemiAutoCycle.ScoreCoral(drivebase, m_frontLimelight, m_Elevator, m_Dropper, this))
    .onFalse(new SequentialCommandGroup(
    new InstantCommand(()-> m_Dropper.restartAutoOutake()),
    new InstantCommand (()-> m_Elevator.ChangeTargetStage(0))));




  // ##### DRIVER CONTROLS #####

    // see SEMI-AUTOs FOR TELEOP above ^^^

    // default driving. turning is left-right on turn joystick, field-rel translation
    drivebase.removeDefaultCommand();
    drivebase.setDefaultCommand(new AbsoluteDriveAdv(
      drivebase, 
      () -> deadband(-drive_joystick.getY(), 0.05), 
      () -> deadband(-drive_joystick.getX(), 0.05), 
      () -> deadband(angle_joystick.getX(), 0.05),

      ()->false,()->false,()->false,()->false
      )
    );

    // point turning, feild rel drive [when turning joystick trigger is held]
    new JoystickButton(angle_joystick, 2).whileTrue(
      new AbsoluteDrive(drivebase, 
        () -> deadband(-drive_joystick.getY(), 0.05), 
        () -> deadband(-drive_joystick.getX(), 0.05), 
        () -> deadband(-angle_joystick.getX(), 0.05),
        () -> deadband(-angle_joystick.getY(), 0.05)
      ) 
    );

    // change auto offest [on driver joystick: 5 sets offset left, 6 sets offset right]
    new JoystickButton(drive_joystick, 5).onTrue(new InstantCommand(()-> this.x_offset = this.x_offset_left)); //this.x_offset_left
    new JoystickButton(drive_joystick, 6).onTrue(new InstantCommand(()-> this.x_offset = this.x_offset_right)); //this.x_offset_right


    new JoystickButton(angle_joystick, 4).whileTrue(
      new SequentialCommandGroup(
        new InstantCommand(()->System.out.println(m_backLimelight.DDDx3_data3D[2])),
        new InstantCommand(()->System.out.println(m_backLimelight.DDDx3_data3D[0])),
        new InstantCommand(()->System.out.println(m_backLimelight.DDDx3_data3D[4]))

      )
    );
    // reset the gyro [angle joystick button 3]
    new JoystickButton(angle_joystick, 3).onTrue(new InstantCommand(drivebase::zeroGyro));





  // ##### OPERATOR CONTROLS ##### 

    // sets auto elevator stages
    // X: level 0 Y: level 3 B: level 2  A: level 1
    new JoystickButton(m_XboxController, 4).onTrue(new InstantCommand(()->this.semi_auto_el_level = 3));
    new JoystickButton(m_XboxController, 3).onTrue(new InstantCommand(()->this.semi_auto_el_level = 2));
    new JoystickButton(m_XboxController, 2).onTrue(new InstantCommand(()->this.semi_auto_el_level = 1));

    
    new JoystickButton(m_XboxController, 1).onTrue(new SequentialCommandGroup(
      
    new InstantCommand(()-> m_Dropper.should_intake = false),
      new InstantCommand(()-> m_Dropper.restartAutoOutake()),
      new InstantCommand(()-> m_Elevator.ChangeTargetStage(0))

    ));

    // ElevatorTweakout button
    new JoystickButton(m_XboxController, 9).onTrue(
      
      new SequentialCommandGroup(
        new InstantCommand(()-> m_Elevator.SetMotor(0.2)),
        new WaitCommand(0.2),
        new InstantCommand(()-> m_Elevator.SetMotor(-0.14))
      ));


    // override auto elvelator, push it up [on operator right trigger]
    new JoystickButton(m_XboxController, 8)
      .onTrue(new InstantCommand(()-> m_Elevator.SetMotor(0.4)))
      .onFalse(new InstantCommand(()-> m_Elevator.SetMotor(0.02)));

    // override auto elvelator, push it down [on operator left trigger]
    new JoystickButton(m_XboxController, 7)
      .onTrue(new InstantCommand(()-> m_Elevator.SetMotor(-0.28)))
      .onFalse(new InstantCommand(()-> m_Elevator.SetMotor(0.02)));
    
    // suck in the coral (backwards robotbot wise) [operator left bumper]
    new JoystickButton(m_XboxController, 5)
      .whileTrue(new InstantCommand(()-> m_Dropper.setMotor(-0.25)))
      .onFalse(new InstantCommand(()-> m_Dropper.restartAutoOutake()));

    // push out the coral [operator right bumper]
    new JoystickButton(m_XboxController, 6)
      .whileTrue(new InstantCommand(()-> m_Dropper.setMotor(0.25)))
      .onFalse(new InstantCommand(()-> m_Dropper.restartAutoOutake()));


  
  // BACKUP controls

    // sets auto elevator stages
    // X: level 0 Y: level 3 B: level 2  A: level 1
    new JoystickButton(BackupController, 4).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(3)));
    new JoystickButton(BackupController, 3).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(2)));
    new JoystickButton(BackupController, 2).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(1)));
    new JoystickButton(BackupController, 1).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(0)));
    
    new JoystickButton(BackupController, 10).onTrue(new SequentialCommandGroup(
      new InstantCommand(()-> m_Dropper.should_intake = false),
      new InstantCommand(()-> m_Dropper.restartAutoOutake()),
      new InstantCommand(()-> m_Elevator.ChangeTargetStage(0))

    ));

    // ElevatorTweakout button
    new JoystickButton(BackupController, 9).onTrue(
      
      new SequentialCommandGroup(
        new InstantCommand(()-> m_Elevator.SetMotor(0.2)),
        new WaitCommand(0.2),
        new InstantCommand(()-> m_Elevator.SetMotor(-0.14))
      ));


    // override auto elvelator, push it up [on operator right trigger]
    new JoystickButton(BackupController, 8)
      .onTrue(new InstantCommand(()-> m_Elevator.SetMotor(0.4)))
      .onFalse(new InstantCommand(()-> m_Elevator.SetMotor(0.02)));

    // override auto elvelator, push it down [on operator left trigger]
    new JoystickButton(BackupController, 7)
      .onTrue(new InstantCommand(()-> m_Elevator.SetMotor(-0.28)))
      .onFalse(new InstantCommand(()-> m_Elevator.SetMotor(0.02)));
    
    // suck in the coral (backwards robotbot wise) [operator left bumper]
    new JoystickButton(BackupController, 5)
      .whileTrue(new InstantCommand(()-> m_Dropper.setMotor(-0.25)))
      .onFalse(new InstantCommand(()-> m_Dropper.restartAutoOutake()));

    // push out the coral [operator right bumper]
    new JoystickButton(BackupController, 6)
      .whileTrue(new InstantCommand(()-> m_Dropper.setMotor(0.25)))
      .onFalse(new InstantCommand(()-> m_Dropper.restartAutoOutake()));




      
  }


  public Command getAutonomousCommand()
  {
    // get data from the SD selector
    return drivebase.getAutonomousCommand(m_auto_chooser.getSelected());
  }

  public void setDriveMode() { configureBindings(); }
  public void setMotorBrake(boolean brake) { drivebase.setMotorBrake(brake);}




  public void periodic(){
    
    Rotation2d m_rotation = new Rotation2d(m_backLimelight.Pos_data[5]);

    Pose2d m_pos2d = new Pose2d(m_backLimelight.Pos_data[0],m_backLimelight.Pos_data[2],m_rotation);

    current_field.setRobotPose(m_pos2d);

    SmartDashboard.putData(current_field);

    

  }
}

