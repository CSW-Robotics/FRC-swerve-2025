// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


// Never Forget: ransomware, siblings, Big "P", limelight chandelier, 60 factorial, greenery, and sound effects 
package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Cmd_LimeLightTracking;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDrive;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDriveAdv;
import frc.robot.commands.swervedrive.drivebase.AbsoluteFieldDrive;
import frc.robot.commands.swervedrive.drivebase.TeleopDrive;
import frc.robot.subsystems.CoralOutput;
import frc.robot.subsystems.Dropper;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import javax.naming.PartialResultException;

import swervelib.SwerveInputStream;
import swervelib.imu.AnalogGyroSwerve;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                                "swerve/neo"));

  private final LimeLight m_Limelight = new LimeLight("limelight");
  private final Elevator m_Elevator = new Elevator();
  private final Dropper m_Dropper = new Dropper();
  private final CoralOutput m_CoralOutput = new CoralOutput();

  public XboxController m_XboxController = new XboxController(2);
  public Joystick drive_joystick = new Joystick(0);
  public Joystick angle_joystick = new Joystick(1);

  // auto picker
  private static final String default_auto = "Test Auto";
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
 
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {

    // get all the files in the pathplanner/autos dir
    File[] files_in_deploy_folder = new File(
      Filesystem.getDeployDirectory(),"pathplanner/autos").listFiles((dir, name) -> name.endsWith(".auto")
    );
    
    // and then add them to a list
    for (File i_file : files_in_deploy_folder) {
      if (i_file.isFile()) {
        System.out.println(i_file.getName());
        //m_chooser.addOption(i_file.getName(), i_file.getName());
      }
    }
    // put it on SmartDashboard
    m_chooser.setDefaultOption("Test Auto", default_auto);
    SmartDashboard.putData("Auto Chooser", m_chooser);
    
    
    

    configureBindings();

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {
    NamedCommands.registerCommand("FreezeWheels", new TeleopDrive(drivebase, ()->0.0, ()->0.0, ()->0.0, ()->true ));

// Dropping coral functions:
// 1st not auto 2nd auto.

// Drops the coral by opening the solenoid,
// then waiting for the coral to fall down the ramp
// then closes the solenoid to reset it.


    // this is for AUTO, and only AUTO. 
    // it is fundamentally the same as the code below assigned to the x button
    NamedCommands.registerCommand("DropCoral", // command to drop coral

      new SequentialCommandGroup( // open the solenoid, wait 2s, the close
        new InstantCommand(() -> m_CoralOutput.setSolenoid(true)), // opens the solenoid

        new ParallelRaceGroup( // wait for 2 secs, lock wheels
          new WaitCommand(2.0),
          new TeleopDrive(drivebase, ()->0.0, ()->0.0, ()->0.0, ()->true )
          // we lock the wheels or otherwise they continue to spin which causes problems,
          // since this ability works best only with a stational robot
        ),

        new InstantCommand(() -> m_CoralOutput.setSolenoid(false)) // closes the solenoid
      )

    );
      



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
      ));


    // binds the buttons on the drive stick to allow us to overide the automatic movement of the elevator.
    new JoystickButton(m_XboxController, 8)
      .onTrue(new InstantCommand(()-> m_Elevator.SetMotor(0.2)))
      .onFalse(new InstantCommand(()-> m_Elevator.SetMotor(0.01)));

    new JoystickButton(m_XboxController, 7)
      .onTrue(new InstantCommand(()-> m_Elevator.SetMotor(-0.2)))
      .onFalse(new InstantCommand(()-> m_Elevator.SetMotor(0.01)));

    // restarts the automatic movement of the elevator
    new JoystickButton(m_XboxController, 10).onTrue(new InstantCommand(()-> m_Elevator.RestartAutoMovement()));

    
    // binds the buttons to output the coral
    new JoystickButton(m_XboxController, 6)
      .whileTrue(new InstantCommand(()-> m_Dropper.setMotor(0.3)))
      .onFalse(new InstantCommand(()-> m_Dropper.setMotor(0.0)));

    // binds the buttons to input the coral
    new JoystickButton(m_XboxController, 5)
      .whileTrue(new InstantCommand(()-> m_Dropper.setMotor(-0.3)))
      .onFalse(new InstantCommand(()-> m_Dropper.setMotor(0.0)));

    new JoystickButton(m_XboxController, 4).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(3)));
    new JoystickButton(m_XboxController, 3).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(2)));
    new JoystickButton(m_XboxController, 2).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(1)));
    new JoystickButton(m_XboxController, 1).onTrue(new InstantCommand(()->m_Elevator.ChangeTargetStage(0)));


    // a button to start limelight tracking
    new JoystickButton(angle_joystick, 12).whileTrue(new Cmd_LimeLightTracking(drivebase,m_Limelight));
    
    // a button to reset the gyro
    new JoystickButton(drive_joystick, 3).onTrue(new InstantCommand(drivebase::zeroGyro) );


    // absolute drive that switches to robot relative
    // teleop drive turning, feild rel drive
    new JoystickButton(angle_joystick, 1).whileTrue( new AbsoluteDriveAdv(
      drivebase, 
      () -> -drive_joystick.getY(), 
      () -> -drive_joystick.getX(), 
      () -> -angle_joystick.getX(),

      //checks what quadrent the angle is in and sets the two closest axis variables to true
      // the != -1 checks to make sure the knob is moves as -1 is the default possition
      // this in effect gives you field oriented control on the knob with fine tuning with left and right on the x axis of the joystick

      () -> ((angle_joystick.getPOV(0) > 90 && angle_joystick.getPOV(0) < 270) && (angle_joystick.getPOV(0) != -1)), 
      () -> ((angle_joystick.getPOV(0) > 270 || angle_joystick.getPOV(0) < 90) && (angle_joystick.getPOV(0) != -1)),  
      () -> ((angle_joystick.getPOV(0) > 180 && angle_joystick.getPOV(0) < 359) && (angle_joystick.getPOV(0) != -1)),
      () ->((angle_joystick.getPOV(0) > 0 && angle_joystick.getPOV(0) < 180) && (angle_joystick.getPOV(0) != -1))
      
      ));

    drivebase.removeDefaultCommand();
    drivebase.setDefaultCommand(
      new AbsoluteDrive(drivebase, 
        () -> -drive_joystick.getY(), 
        () -> -drive_joystick.getX(), 
        () -> angle_joystick.getX(),
        () -> -angle_joystick.getY()
      )
    );
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    return drivebase.getAutonomousCommand(m_chooser.getSelected());

  }

  public void setDriveMode()
  {
    configureBindings();
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
