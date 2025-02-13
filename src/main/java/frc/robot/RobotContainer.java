// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDrive;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDriveAdv;
import frc.robot.commands.swervedrive.drivebase.AbsoluteFieldDrive;
import frc.robot.commands.swervedrive.drivebase.TeleopDrive;
import frc.robot.subsystems.CoralOutput;
import frc.robot.subsystems.Dropper;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;
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

  private final LimeLight m_Limelight = new LimeLight();
  private final Elevator m_Elevator = new Elevator();
  private final Dropper m_dropper = new Dropper();
  private final CoralOutput m_CoralOutput = new CoralOutput();

  public Joystick drive_joystick = new Joystick(0);
  public Joystick angle_joystick = new Joystick(1);
  
 
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    //regaster pathplanner commands here
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
    NamedCommands.registerCommand("OutputCoral", new InstantCommand(() -> auto_CoralOutput.openSolenoid()));


    new JoystickButton(angle_joystick, 7).onTrue(new InstantCommand(drivebase::zeroGyro) );

    new JoystickButton(angle_joystick, 8).onTrue(new InstantCommand(() -> m_CoralOutput.setSolenoid(true)));
    new JoystickButton(angle_joystick, 8).onFalse(new InstantCommand(() -> m_CoralOutput.setSolenoid(false)));



    // binds the buttons on the drive stick to allow us to overide the automatic movement of the elevator.
    new JoystickButton(drive_joystick, 3).onTrue((new InstantCommand(()-> m_Elevator.SetMotor(0.3))));
    new JoystickButton(drive_joystick, 3).onFalse((new InstantCommand(()-> m_Elevator.SetMotor(0))));

    new JoystickButton(drive_joystick, 2).onTrue((new InstantCommand(()-> m_Elevator.SetMotor(-0.3))));
    new JoystickButton(drive_joystick, 2).onFalse((new InstantCommand(()-> m_Elevator.SetMotor(0))));

    // restarts the automatic movement of the elevator
    new JoystickButton(drive_joystick, 5).onTrue(new InstantCommand(()-> m_Elevator.RestartAutoMovement()));

    // binds the buttons to intake and output the coral
    new JoystickButton(angle_joystick, 5).whileTrue((new InstantCommand(()-> m_dropper.takeIn())));
    new JoystickButton(angle_joystick, 3).whileTrue((new InstantCommand(()-> m_dropper.pushOut())));

    // stops the motors when the button is released
    new JoystickButton(angle_joystick, 5).onFalse((new InstantCommand(()-> m_dropper.startMotors(0.0))));
    new JoystickButton(angle_joystick, 3).onFalse((new InstantCommand(()-> m_dropper.startMotors(0.0))));

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
    return drivebase.getAutonomousCommand("Test Auto");

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
