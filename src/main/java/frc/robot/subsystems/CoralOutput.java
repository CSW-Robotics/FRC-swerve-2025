package frc.robot.subsystems;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;



// THE ACTUAL WAY:
// It's going to be a solenoid that's used for the auto ranking point
// Two functions to turn it on and to turn it off.
// Don't forget to define it as well.




// class declaration and inheritance
public class CoralOutput extends SubsystemBase {

    // create the physical objects that the subsytem uses
    // THE SOLENOID
    private Solenoid m_Solenoid = new Solenoid(PneumaticsModuleType.REVPH, 0);
    

// either set true or false for turning on the solenoid
// true is on
// false is off
  public void setSolenoid(boolean state) {
    m_Solenoid.set(state);
  }




  @Override
  public void periodic() {
    // WPILIB MUST HAVE - this method will be called once per scheduler run - 20ms
    
    //System.out.print("Solenoid Channel: ");
    //System.out.println(m_Solenoid.getChannel());

    System.out.print("Solenoid State: ");
    System.out.println(m_Solenoid.get());

    //System.out.print("Solenoid Class: ");
    //System.out.println(m_Solenoid.getClass());
  }

  @Override
  public void simulationPeriodic() {
    // WPILIB MUST HAVE - this method will be called once per scheduler run during simulation
  }
}