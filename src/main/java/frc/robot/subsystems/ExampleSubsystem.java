// This is an example subsystem made by me, Max Lawton, to show the struare of a subsystem

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {

    private SparkMax test_motor = new SparkMax(0, MotorType.kBrushless);
    private DigitalInput test_input = new DigitalInput(0);

// constructor - will be run ONCE when this class, ExampleSubsystem, is created
  public ExampleSubsystem() {
    // run code that only needs to be done once
    // such as setting presets
  }

// demonstrative method that returns the state of the encoder
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