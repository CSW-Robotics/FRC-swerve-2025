package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

  import com.revrobotics.Rev2mDistanceSensor;
  import com.revrobotics.Rev2mDistanceSensor.Port;
  import com.revrobotics.Rev2mDistanceSensor.Unit;


public class Dropper extends SubsystemBase {
    
    private SparkMax m_Dropper = new SparkMax(20, MotorType.kBrushless);
    // private DigitalInput coral_in = new DigitalInput(0);

    private SparkMaxConfig spark_config = new SparkMaxConfig();

    private Rev2mDistanceSensor distOnboard;  
    
    private boolean overide_automatic_output = false;

  public Dropper() {
    spark_config.idleMode(SparkBaseConfig.IdleMode.kBrake);
    m_Dropper.configure(spark_config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);

    distOnboard = new Rev2mDistanceSensor(Port.kOnboard);
    distOnboard.setAutomaticMode(true);
    distOnboard.setDistanceUnits(Unit.kMillimeters);
  }
public void restartAutoOutake(){

  setMotor(0);

  overide_automatic_output = false;

}

 
//set the motor speed
  public void setMotor(double speed) {

      overide_automatic_output = true;

    // sets motors to speed
    m_Dropper.set(speed);
    
    }


  public void periodic(){

    //System.out.println(distOnboard.getRange());


    if (110>distOnboard.getRange() && distOnboard.getRange()>5 && overide_automatic_output == false) {

      // i dont think the distances on the above if statement is right
      // so i am printing out the distance sensor data, adjust as needed

      setMotor(0.2);

      System.out.println("coral detected intaking");

    }

    else if (overide_automatic_output == false){

      setMotor(0);

      System.out.println("freezing dropper motor");
    }
    

  }



}



