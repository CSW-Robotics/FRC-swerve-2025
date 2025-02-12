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
public class Dropper extends SubsystemBase {

    // create the physical objects that the subsytem uses
    // motors, sensors,...
    private SparkMax dropper_motor = new SparkMax(0, MotorType.kBrushless);
    private DigitalInput coralIn = new DigitalInput(0);

  // demonstrative method that returns the state of the digital input
  public boolean getInput() { 
    return coralIn.get();
  }

// demonstrative method that sets the motor speed
  public void startMotors(double speed) {
    dropper_motor.set(speed);
    }
  

  public void stopMotors() {
   dropper_motor.set(0); 
  }

  public void takeIn() {
    if (getInput() == false){
      startMotors(.05);
    }
    if (getInput() == true) {
      stopMotors();
    }
  }

  public void pushOut() {
    if (getInput() == true){
      startMotors(.05);
    if (getInput() == false) {
      stopMotors();
    }
    }
  }

  @Override
  public void periodic() {
    // WPILIB MUST HAVE - this method will be called once per scheduler run - 20ms
    System.out.println(dropper_motor.getEncoder().getVelocity()); // print motor velocity
    System.out.println(coralIn.get());
  }

  @Override
  public void simulationPeriodic() {
    // WPILIB MUST HAVE - this method will be called once per scheduler run during simulation
  } 
}