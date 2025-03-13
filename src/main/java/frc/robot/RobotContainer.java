// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


// Never Forget: ransomware, siblings, Big "P", limelight chandelier, 60 factorial, greenery, and sound effects 
package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DieOnDoneTracking;
import frc.robot.commands.DieOnElevatorLevel;
import frc.robot.commands.DieOnTag;
import frc.robot.commands.LimelightTracking;
import frc.robot.commands.Traversals;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDrive;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDriveAdv;
import frc.robot.commands.swervedrive.drivebase.TeleopDrive;
import frc.robot.subsystems.CoralOutput;
import frc.robot.subsystems.Dropper;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;

import javax.lang.model.element.Parameterizable;

// our robot container object
public class RobotContainer
{
  // subsystems
  private final SwerveSubsystem drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve/neo"));
  private final LimeLight m_backLimelight = new LimeLight("limelight");
  private final LimeLight m_frontLimelight = new LimeLight("limelight-front");
  private final Elevator m_Elevator = new Elevator();
  private final Dropper m_Dropper = new Dropper();
  private final CoralOutput m_CoralOutput = new CoralOutput();

  // controllers
  public XboxController m_XboxController = new XboxController(2);
  public Joystick drive_joystick = new Joystick(1);
  public Joystick angle_joystick = new Joystick(0);

  // auto picker
  private static final String default_auto = "Test Auto";
  private final SendableChooser<String> m_auto_chooser = new SendableChooser<>();
  
  // x offests of the auto tracking
  public double x_offset_left =  -0.168;
  public double x_offset_right =  0.172;
  public double x_offset = x_offset_right;

  // SemiAuto Elevator Level Chooser for SD
  private final SendableChooser<Integer> auto_elevator_level_chooser = new SendableChooser<>();
  
 
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
    m_auto_chooser.setDefaultOption("Test Auto", default_auto);
    SmartDashboard.putData("Auto Chooser Mk3", m_auto_chooser);




  // ##### SEMIAUTO LEVEL PICKER ON SD ####

    auto_elevator_level_chooser.setDefaultOption("Level 2", 1);
    auto_elevator_level_chooser.addOption("Level 3", 2);
    auto_elevator_level_chooser.addOption("Level 4", 3);
    SmartDashboard.putData("SemiAuto Elevator Level Chooser", auto_elevator_level_chooser);



  

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

    // back limelight tracking
    NamedCommands.registerCommand("LimelightTrackingBack", 
      LimelightTracking.Back(drivebase, m_backLimelight)
    );

    // tracks to front, places anywhere on reef (not level 1)
    NamedCommands.registerCommand("LimelightTrackingFront", 
      new SequentialCommandGroup(
        new InstantCommand (()->m_Elevator.ChangeTargetStage(2)),
        new WaitCommand(1.5),
        new ParallelRaceGroup(
          new WaitCommand(5),
          LimelightTracking.Front(drivebase, m_frontLimelight, this)
        ),
        new InstantCommand(() -> m_Elevator.ChangeTargetStageFromChooser(auto_elevator_level_chooser)),
        new WaitCommand(1.5),
        new InstantCommand(()-> m_Dropper.setMotor(0.2)),
        new WaitCommand(1),
        new InstantCommand( ()-> m_Dropper.setMotor(0)),
        new InstantCommand( ()-> m_Elevator.ChangeTargetStage(0))
      )
    );





  // ##### SEMI-AUTOs FOR TELEOP #####

    // a button to start limelight tracking (from the front) [on driving joystick trigger]
    new JoystickButton(drive_joystick, 1).whileTrue(
      LimelightTracking.Front(drivebase, m_frontLimelight, this)
    );


    // traverse in [driver button 3]
    new JoystickButton(drive_joystick, 3).whileTrue(
      new SequentialCommandGroup(
        new InstantCommand (()-> m_Elevator.ChangeTargetStage(0)),
        Traversals.In(drivebase, m_frontLimelight, 1),
        LimelightTracking.Back(drivebase, m_backLimelight),
        new DieOnDoneTracking(m_backLimelight, 1)
      )
    );

    // traverse out [driver button 4]
    new JoystickButton(drive_joystick, 4).whileTrue(
      new SequentialCommandGroup(
        new InstantCommand (()-> m_Elevator.ChangeTargetStage(2)),
        new ParallelRaceGroup(
          Traversals.Out(drivebase, 1, true),
          new DieOnTag(m_frontLimelight, 19)
        ),
        new ParallelRaceGroup(
          LimelightTracking.Front(drivebase, m_frontLimelight, this),
        
          new SequentialCommandGroup(
            new DieOnDoneTracking(m_frontLimelight, 19),
            new DieOnElevatorLevel(m_Elevator, 3),
            new InstantCommand (()->m_Dropper.setMotor(0.2)),
            new WaitCommand(0.5),
            new InstantCommand (()->m_Dropper.setMotor(0.0))
      )))
    );




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
    new JoystickButton(angle_joystick, 1).whileTrue(
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

    // reset the gyro [angle joystick button 3]
    new JoystickButton(angle_joystick, 3).onTrue(new InstantCommand(drivebase::zeroGyro));





  // ##### OPERATOR CONTROLS ##### 

    // sets auto elevator stages
    // X: level 0 Y: level 3 B: level 2  A: level 1
    new JoystickButton(m_XboxController, 4).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(3)));
    new JoystickButton(m_XboxController, 3).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(2)));
    new JoystickButton(m_XboxController, 2).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(1)));
    new JoystickButton(m_XboxController, 1).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(0)));
 
    // override auto elvelator, push it up [on operator right trigger]
    new JoystickButton(m_XboxController, 8)
      .onTrue(new InstantCommand(()-> m_Elevator.SetMotor(0.2)))
      .onFalse(new InstantCommand(()-> m_Elevator.SetMotor(0.04)));

    // override auto elvelator, push it down [on operator left trigger]
    new JoystickButton(m_XboxController, 7)
      .onTrue(new InstantCommand(()-> m_Elevator.SetMotor(-0.2)))
      .onFalse(new InstantCommand(()-> m_Elevator.SetMotor(0.04)));
    
    // suck in the coral (backwards robotbot wise) [operator left bumper]
    new JoystickButton(m_XboxController, 5)
      .whileTrue(new InstantCommand(()-> m_Dropper.setMotor(-0.2)))
      .onFalse(new InstantCommand(()-> m_Dropper.restartAutoOutake()));

    // push out the coral [operator right bumper]
    new JoystickButton(m_XboxController, 6)
      .whileTrue(new InstantCommand(()-> m_Dropper.setMotor(0.2)))
      .onFalse(new InstantCommand(()-> m_Dropper.restartAutoOutake()));

    // open coral drop solenoids [operator button 9, also called "BACK" ]
    new JoystickButton(m_XboxController, 9).onTrue(
      new SequentialCommandGroup(
        new InstantCommand(() -> m_CoralOutput.setSolenoid(true)), // Turns on the solenoid, which retracts it
        new ParallelRaceGroup( 
          new WaitCommand(2.0), // wait for 2 secs 
          new TeleopDrive(drivebase, ()->0.0, ()->0.0, ()->0.0, ()->true ) // and also lock the wheels
          // we lock the wheels or otherwise they continue to spin which causes problems,
          // since this ability works best only with a stational robot
        ),
        new InstantCommand(() -> m_CoralOutput.setSolenoid(false)) // Then extends it again, which completes the cycle
      )
    );




    
  }


  public Command getAutonomousCommand()
  {
    // get data from the SD selector
    return drivebase.getAutonomousCommand(m_auto_chooser.getSelected());
  }

  public void setDriveMode() { configureBindings(); }
  public void setMotorBrake(boolean brake) { drivebase.setMotorBrake(brake);}
}
