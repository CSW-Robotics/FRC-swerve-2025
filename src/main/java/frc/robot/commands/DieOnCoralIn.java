package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Dropper;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;

public class DieOnCoralIn extends Command{

    private final Dropper m_Dropper;
    private double m_z_finish;

    public DieOnCoralIn(Dropper dropper) {
        m_Dropper = dropper;
        addRequirements(dropper);
    }

    @Override
    public boolean isFinished() {
        if (m_Dropper.distOnboard.getRange() < 110) {
            return true;
        } else {
            return false;

        }
    }
    
}
