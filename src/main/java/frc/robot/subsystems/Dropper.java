package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;


public class Dropper extends SubsystemBase {

    private final I2C.Port i2cPort = I2C.Port.kOnboard; // defines the i2c port

    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort); // defines the color sensor

    private final ColorMatch m_colorMatcher = new ColorMatch(); // defines the color match

     private final Color kWhiteTarget = new Color(255, 255, 255); // defines the target

    public boolean ShouldIntake = false;
    


    private SparkMax m_Dropper = new SparkMax(20, MotorType.kBrushless);
    // private DigitalInput coral_in = new DigitalInput(0);

    private SparkMaxConfig spark_config = new SparkMaxConfig();

  public Dropper() {
    spark_config.idleMode(SparkBaseConfig.IdleMode.kBrake);
    m_Dropper.configure(spark_config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);

    m_colorMatcher.addColorMatch(kWhiteTarget); // adds the white target to the color match
  }

 
//set the motor speed
  public void setMotor(double speed) {
    // sets motors to speed
    m_Dropper.set(speed);
    }

  public void StartIntake(){

    ShouldIntake = true;

  }


  public void periodic(){

    if (ShouldIntake = true) {

      Color detectedColor = m_colorSensor.getColor(); // defines a variable to hold our color

      if (detectedColor == kWhiteTarget) { // if detected color is white
        m_Dropper.set(0.3); // sets the dropper to intake
      }

      else { // if the color is not white
        m_Dropper.set(0.0); // sets the dropper to stop outputting
        ShouldIntake = false;

      }

    }

  }



}



