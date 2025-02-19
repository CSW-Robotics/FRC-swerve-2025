package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralOutput extends SubsystemBase {    


  // solenoid is on the pdh switchable channel
  // instead of the typical solenoid type
  // just make sure it's connected to the pdh

  private PowerDistribution pdh = new PowerDistribution(1, PowerDistribution.ModuleType.kRev);

  public CoralOutput() {
    
    // FALSE: Extended
    // TRUE: Retracted

    // starts at false because the coral is going to dropped
    // by retracting the solenoid, allowing gravity to drop it 
    pdh.setSwitchableChannel(false);
  }

  public void setSolenoid(boolean state) {

    // accepts true or false
    // false is extended
    // true is retracted x2
    pdh.setSwitchableChannel(state);
  }

  // "you don't need the periodic functions"
  // MAX SAID IT WAS A DARN REQUIREMENT
}