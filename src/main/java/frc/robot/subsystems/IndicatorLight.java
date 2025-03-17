package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LEDs;

public class IndicatorLight extends SubsystemBase {

    private final LEDs m_led;
    private String m_led_previous = "Off";
    private final Elevator m_elevator;
    private final LimeLight m_limelight;
    private final Dropper m_dropper;

    public IndicatorLight(LEDs led, Elevator elevator, LimeLight limeLight, Dropper dropper) {
        m_led = led;
        m_elevator = elevator;
        m_limelight = limeLight;
        m_dropper = dropper;

    }

    @Override
    public void periodic(){

        if (m_elevator.currentStage == 0 && m_limelight.DDDx3_data3D[2] < 0.5 && m_led_previous != "Green"){
            // set LEDs to green
            m_led_previous = "Green";
        } 

        if (m_elevator.currentStage == 0 && m_limelight.DDDx3_data3D[2] < 0.5 && m_dropper.distOnboard.getRange() < 110 && m_led_previous != "Blue"){
            // set LEDs to Blue
            m_led_previous = "Blue";
        } 
        
        else if (m_dropper.distOnboard.getRange() > 110 && m_led_previous != "Off"){
            // turn LEDs off
            m_led_previous = "Off";
        }
        
  
    }

}
