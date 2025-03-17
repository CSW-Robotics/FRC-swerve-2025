package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;

public class LEDs {

    // vars
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;
    private LEDPattern color;

    public LEDs() {
        // connect leds to PWM port 0
        m_led = new AddressableLED(0);

        // make buffer
        m_ledBuffer = new AddressableLEDBuffer(60);
        m_led.setLength(m_ledBuffer.getLength());

        // set color
        color = LEDPattern.solid(Color.kDeepPink);
        color.applyTo(m_ledBuffer);

        // set color data and start
        m_led.setData(m_ledBuffer);
        m_led.start();
        
    }
    
}
