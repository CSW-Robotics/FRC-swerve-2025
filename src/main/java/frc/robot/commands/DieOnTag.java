package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LimeLight;

public class DieOnTag extends Command{

    private final LimeLight m_limelight;
    private final int m_tag_to_die_on;

    public DieOnTag(LimeLight limelight, int tag_to_die_on) {
        m_limelight = limelight;
        m_tag_to_die_on = tag_to_die_on;
        addRequirements(limelight);
    }

    @Override
    public boolean isFinished() {
        if (m_limelight.tid == m_tag_to_die_on) {
            return true;
        } else {
            return false;

        }
    }
    
}
