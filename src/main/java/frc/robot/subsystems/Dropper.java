package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;


public class Dropper extends SubsystemBase {

    private SparkMax dropper_motor = new SparkMax(0, MotorType.kBrushless);
    private DigitalInput coral_in = new DigitalInput(0);

  // demonstrative method that returns the state of the digital input
  public boolean getInput() { 
    // gets t/f for limit switch
    return coral_in.get();
  }

//set the motor speed
  public void setMotor(double speed) {
    // sets motors to speed
    dropper_motor.set(speed);
    }

  public void takeIn() {
    // method run when taking in coral, runs motors until limit switch is true
    if (getInput() == false) {
      setMotor(.05);
    }
    if (getInput() == true) {
      setMotor(0.0);
    }
  }

  public void pushOut() {
    // method run when pushing coral out, runs motors until limit switch is false
    if (getInput() == true) {
      setMotor(.05);
    }
    if (getInput() == false) {
      setMotor(0.0);
    }
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {} 
}