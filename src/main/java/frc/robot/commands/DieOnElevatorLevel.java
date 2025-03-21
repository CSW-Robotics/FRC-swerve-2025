package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;

public class DieOnElevatorLevel extends Command{

    private final Elevator m_elevator;
    private final RobotContainer m_robot_container;

    public DieOnElevatorLevel(Elevator elevator, RobotContainer robot_container) {
        m_elevator = elevator;
        m_robot_container = robot_container;
        addRequirements(m_elevator);
    }

    @Override
    public boolean isFinished() {
        if (m_elevator.currentStage == m_robot_container.semi_auto_el_level) {
            return true;
        } else {
            return false;
        }
    }
    
}
