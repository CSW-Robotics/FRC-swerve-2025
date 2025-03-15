package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;

public class DieOnElevatorLevel extends Command{

    private final Elevator m_elevator;
    private final int m_target_stage;

    public DieOnElevatorLevel(Elevator elevator, int target_stage) {
        m_elevator = elevator;
        m_target_stage = target_stage;
        addRequirements(m_elevator);
    }

    @Override
    public boolean isFinished() {
        if (m_elevator.currentStage == m_target_stage) {
            return true;
        } else {
            return false;

        }
    }
    
}
