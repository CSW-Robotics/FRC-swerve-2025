package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Dropper;

public class DieOnIntaken extends Command {

    private Dropper m_dropper;
    
    public DieOnIntaken(Dropper dropper){

        m_dropper = dropper;

        addRequirements(m_dropper);

    }

    public boolean isFinished(){

        if (m_dropper.should_intake == false){

            return true;

        }

        else{

            return false;
        }
        
    }
    
}
