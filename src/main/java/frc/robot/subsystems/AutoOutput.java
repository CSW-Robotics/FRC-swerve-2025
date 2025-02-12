// This is an example subsystem made by me, Max Lawton, to show the structure of a subsystem
// For this class you must have the folloing
// 1. extend the SubsystemBase in the declaration (lines 15-16)
// 2. have a constructor (lines 23-28)
// 3. include (void)methods periodic() and simulationPeriodic()

// imports, usually done automatically by vscode
package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

// class declaration and inheritance
public class AutoOutput extends SubsystemBase {

    // create the physical objects that the subsytem uses
    // motors, sensors,...
    private SparkMax test_motor = new SparkMax(0, MotorType.kBrushless);
    private DigitalInput test_input = new DigitalInput(0);

// constructor - will be run ONCE when this class, ExampleSubsystem, is instantiated
// used to run code that only needs to be done once, such as setting presets or constants
// must have exactly the same name as the class
// also, this is where other subsystems are passed into this one, such as the drivebase for example
  public AutoOutput(SwerveSubsystem drivebase) {
  }

// demonstrative method that returns the state of the digital input
  public boolean getInput() { 
    return test_input.get();
  }

// demonstrative method that sets the motor speed
  public void setMotorSpeed(double speed) {
    test_motor.set(speed);
  }

  @Override
  public void periodic() {
    // WPILIB MUST HAVE - this method will be called once per scheduler run - 20ms

    System.out.println(test_motor.getEncoder().getVelocity()); // print motor velocity
    System.out.println(test_input.get()); // print state of DigitalInput
  }

  @Override
  public void simulationPeriodic() {
    // WPILIB MUST HAVE - this method will be called once per scheduler run during simulation
  }
}