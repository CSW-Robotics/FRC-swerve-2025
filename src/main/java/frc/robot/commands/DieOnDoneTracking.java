package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;

public class DieOnDoneTracking extends Command{

    private final LimeLight m_limelight;
    private int targetID;

    public DieOnDoneTracking(LimeLight limelight, int targetID) {
        m_limelight = limelight;
        targetID = targetID;
        addRequirements(m_limelight);
    }

    @Override
    public boolean isFinished() {
        if (m_limelight.DDDx3_data3D[2] < 0.7 && m_limelight.tv == 1 && m_limelight.tid == targetID) {
            return true;
        } else {
            return false;

        }
    }
    
}
