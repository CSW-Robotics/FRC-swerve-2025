package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;

public class DieOnElevatorLevel extends Command{

    private final Elevator m_elevator;
    private int target_stage;

    public DieOnElevatorLevel(Elevator elevator, int target_stage) {
        m_elevator = elevator;
        target_stage = target_stage;
        addRequirements(m_elevator);
    }

    @Override
    public boolean isFinished() {
        if (m_elevator.currentStage == target_stage) {
            return true;
        } else {
            return false;

        }
    }
    
}
