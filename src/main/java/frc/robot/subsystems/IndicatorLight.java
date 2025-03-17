package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IndicatorLight extends SubsystemBase {

    private final Elevator m_elevator;
    private final LimeLight m_limelight;
    private final PowerDistribution m_pdh = new PowerDistribution(1, PowerDistribution.ModuleType.kRev);

    public IndicatorLight(Elevator elevator, LimeLight limeLight) {
        m_elevator = elevator;
        m_limelight = limeLight;
    }

    @Override
    public void periodic(){

        if (m_elevator.currentStage == 0 && m_limelight.DDDx3_data3D[2] < 0.5){
            m_pdh.setSwitchableChannel(true);
        } else{
            m_pdh.setSwitchableChannel(false);
        }
  
    }

}
