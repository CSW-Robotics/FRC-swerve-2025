package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

  import com.revrobotics.Rev2mDistanceSensor;
  import com.revrobotics.Rev2mDistanceSensor.Port;
  import com.revrobotics.Rev2mDistanceSensor.Unit;


public class Dropper extends SubsystemBase {
    
    private SparkMax m_Dropper_Motor = new SparkMax(20, MotorType.kBrushless);
    // private DigitalInput coral_in = new DigitalInput(0);

    private SparkMaxConfig spark_config = new SparkMaxConfig();

    public boolean should_intake = false;

  public Dropper() {
    spark_config.idleMode(SparkBaseConfig.IdleMode.kBrake);
    m_Dropper_Motor.configure(spark_config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
  }

public void restartAutoOutake(){
  m_Dropper_Motor.set(0);

}

 
//set the motor speed
  public void setMotor(double speed) {
    // sets motors to speed
    m_Dropper_Motor.set(speed);
    
    }


  public void periodic(){
    

  }



}



