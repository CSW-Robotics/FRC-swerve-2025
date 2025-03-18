package frc.robot;

import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;

public class LEDs {

    // vars
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;
    private LEDPattern color;

    public LEDs() {
        // connect leds to PWM port 1
        m_led = new AddressableLED(1);

        // make buffer
        m_ledBuffer = new AddressableLEDBuffer(60);
        m_led.setLength(m_ledBuffer.getLength());

        // set color and data
        color = LEDPattern.solid(Color.kMagenta);
        color.applyTo(m_ledBuffer);
        m_led.setData(m_ledBuffer);

        //start
        m_led.start();
    }

    public void SetColor(int h, int s, int v) {
        // color = LEDPattern.solid(Color.fromHSV(h, s, v));
        // color.applyTo(m_ledBuffer);
        // m_led.setData(m_ledBuffer);
    }

    public void SetRainbow() {
        // color = LEDPattern.rainbow(255, 128);
        // color = color.scrollAtAbsoluteSpeed(MetersPerSecond.of(1), Meter.of(1/120));
        // color.applyTo(m_ledBuffer);
        // m_led.setData(m_ledBuffer);
    }

    public void FentLights() {
        // color = LEDPattern.solid(Color.kWhite);
        // color = color.blink(Seconds.of(0.1));
        // color.applyTo(m_ledBuffer);
        // m_led.setData(m_ledBuffer);
    }

    public void BaseColor(){

        // this.SetColor(0, 0, 100);

    }
    
}
