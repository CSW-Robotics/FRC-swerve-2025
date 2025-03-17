package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LEDs;

public class IndicatorLight extends SubsystemBase {

    private final LEDs m_led;
    private boolean m_led_previous = false;
    private final Elevator m_elevator;
    private final LimeLight m_limelight;

    public IndicatorLight(LEDs led, Elevator elevator, LimeLight limeLight) {
        m_led = led;
        m_elevator = elevator;
        m_limelight = limeLight;

    }

    @Override
    public void periodic(){

        if (m_elevator.currentStage == 0 && m_limelight.DDDx3_data3D[2] < 0.5 && m_led_previous == false){
           
            m_led_previous = true;
        } 
        
        else if (m_led_previous == true){

            m_led_previous = false;
        }
        
  
    }

}
