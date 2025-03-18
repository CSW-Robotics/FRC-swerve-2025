package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;

public class DieOnDoneTracking extends Command{

    private final LimeLight m_limelight;
    private double m_z_finish;

    public DieOnDoneTracking(LimeLight limelight, double zFinish) {
        m_limelight = limelight;
        m_z_finish = zFinish;
        addRequirements(m_limelight);
    }

    @Override
    public boolean isFinished() {
        if (m_limelight.DDDx3_data3D[2] < m_z_finish && m_limelight.tv == 1) {
            return true;
        } else {
            return false;

        }
    }
    
}
