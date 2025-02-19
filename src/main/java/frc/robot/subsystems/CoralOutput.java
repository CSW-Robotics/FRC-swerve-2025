package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralOutput extends SubsystemBase {    

  // solenoid is on the pdh switchable channel
  private PowerDistribution pdh = new PowerDistribution(0, PowerDistribution.ModuleType.kRev);

  public void setSolenoid(boolean state) {
    pdh.setSwitchableChannel(state);
  }

  // you don't need the periodic functions
}