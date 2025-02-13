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
    // gets t/f for limit switch
    return coralIn.get();
  }

// demonstrative method that sets the motor speed
  public void startMotors(double speed) {
    // sets motors to speed
    dropper_motor.set(speed);
    }
  
  public void stopMotors() {
    // sets speed to 0
   dropper_motor.set(0); 
  }

  public void takeIn() {
    // method run when taking in coral, runs motors until limit switch is true
    if (getInput() == false) {
      startMotors(.05);
    }
    if (getInput() == true) {
      stopMotors();
    }
  }

  public void pushOut() {
    // method run when pushing coral out, runs motors until limit switch is false
    if (getInput() == true) {
      startMotors(.05);
    }
    if (getInput() == false) {
      stopMotors();
    }
  }

  @Override
  public void periodic() {
    // WPILIB MUST HAVE - this method will be called once per scheduler run - 20ms
    System.out.println(dropper_motor.getEncoder().getVelocity()); // print motor velocity
    System.out.println(coralIn.get());
  }

  @Override
  public void simulationPeriodic() {} 
}