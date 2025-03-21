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
        
  
    }

}
