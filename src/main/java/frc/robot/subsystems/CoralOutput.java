// This is an example subsystem made by me, Max Lawton, to show the structure of a subsystem
// For this class you must have the folloing
// 1. extend the SubsystemBase in the declaration (lines 15-16)
// 2. have a constructor (lines 23-28)
// 3. include (void)methods periodic() and simulationPeriodic()

// imports, usually done automatically by vscode
package frc.robot.subsystems;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

// This is the system that will output the coral on the bottom for a ranking point
// Nicholas says: move wheels! DONE.
// We'll make a method for turning on wheels to put the coral

// THE ACTUAL WAY:
// It's going to be a solenoid that's used for the auto ranking point
// Two functions to turn it on and to turn it off.
// Don't forget to define it as well.




// class declaration and inheritance
public class CoralOutput extends SubsystemBase {

    // create the physical objects that the subsytem uses
    // THE SOLENOID
    private Solenoid m_Solenoid = new Solenoid(PneumaticsModuleType.REVPH, 0);

    
    // What is a digital input:


// constructor - will be run ONCE when this class, ExampleSubsystem, is instantiated
// used to run code that only needs to be done once, such as setting presets or constants
// must have exactly the same name as the class
// also, this is where other subsystems are passed into this one, such as the drivebase for example
  public CoralOutput(SwerveSubsystem drivebase) {

  }

// A function to open up the Solenoid

  public void openSolenoid(double speed) {
    m_Solenoid.set(true);
  }

  public void closeSolenoid(double speed) {
    m_Solenoid.set(false);
  }


  @Override
  public void periodic() {
    // WPILIB MUST HAVE - this method will be called once per scheduler run - 20ms
    System.out.print("Solenoid Channel: ");
    System.out.println(m_Solenoid.getChannel());

    System.out.print("Solenoid State: ");
    System.out.println(m_Solenoid.get());

    System.out.print("Class: ");
    System.out.println(m_Solenoid.getClass());
  }

  @Override
  public void simulationPeriodic() {
    // WPILIB MUST HAVE - this method will be called once per scheduler run during simulation
  }
}