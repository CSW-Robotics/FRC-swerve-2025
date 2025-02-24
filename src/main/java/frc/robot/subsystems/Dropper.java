package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;


public class Dropper extends SubsystemBase {

    private SparkMax m_Dropper = new SparkMax(42, MotorType.kBrushless);
    // private DigitalInput coral_in = new DigitalInput(0);

    private SparkMaxConfig spark_config = new SparkMaxConfig();

  public Dropper() {
    spark_config.idleMode(SparkBaseConfig.IdleMode.kBrake);
    m_Dropper.configure(spark_config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
  }

  // // demonstrative method that returns the state of the digital input
  // public boolean getInput() { 
  //   // gets t/f for limit switch
  //   return coral_in.get();
  // }

//set the motor speed
  public void setMotor(double speed) {
    // sets motors to speed
    m_Dropper.set(speed);
    }

  // public void takeIn() {
  //   // method run when taking in coral, runs motors until limit switch is true
  //   if (getInput() == false) {
  //     setMotor(.05);
  //   }
  //   if (getInput() == true) {
  //     setMotor(0.0);
  //   }
  // }

  // public void pushOut() {
  //   // method run when pushing coral out, runs motors until limit switch is false
  //   if (getInput() == true) {
  //     setMotor(.05);
  //   }
  //   if (getInput() == false) {
  //     setMotor(0.0);
  //   }
  // }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {} 
}