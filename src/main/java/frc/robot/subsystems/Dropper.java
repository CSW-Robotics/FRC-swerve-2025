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

    private DutyCycleEncoder m_Encoder = new DutyCycleEncoder(7);

    private boolean overide_automatic_output = false;

    public boolean should_intake = false;

    private double start_pos = m_Encoder.get();

    private double outake_speed = 0.37;

  public Dropper() {
    spark_config.idleMode(SparkBaseConfig.IdleMode.kBrake);
    m_Dropper_Motor.configure(spark_config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
  }

public void restartAutoOutake(){
  overide_automatic_output = false;
  m_Dropper_Motor.set(0);

}

 
//set the motor speed
  public void setMotor(double speed) {
    overide_automatic_output = true;
    // sets motors to speed
    m_Dropper_Motor.set(speed);
    
    }

  public void intakeCoral(double speed){

    outake_speed = speed;
    should_intake = true;
    start_pos = m_Encoder.get();

  }


  public void periodic(){

    double delta = (m_Encoder.get() - start_pos)*-1;

    if (delta < -0.05){
      delta += 1.0;

    }

    double target = 0.8;

    if (should_intake == true && overide_automatic_output == false && delta < target){

      double error = target - delta;
      
      m_Dropper_Motor.set(Math.max((outake_speed*0.4*error), 0.25));

    }

    else if (should_intake == true && overide_automatic_output == false && delta >= target) {

      should_intake = false;
      m_Dropper_Motor.set(0.0);

    }
    

  }



}



