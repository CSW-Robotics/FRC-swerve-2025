package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NewDropper extends SubsystemBase {

    private SparkMax m_motor = new SparkMax(20, MotorType.kBrushless);
    private DutyCycleEncoder m_encoder = new DutyCycleEncoder(7);

    private final double target_motor_velocity = 60; // in rpm
    private final double min_motor_power = 0.2; // in %
    private final double kP = 0.4;

    private final double target_auto_encoder_rotation = 0.8; // in rotations

    private int current_motor_direction = 0;
    private double current_motor_power = 0;
    private boolean auto_intake = false;

    public void manualOut() {
        current_motor_direction = 1;
        auto_intake = false;
    }

    public void manualIn() {
        current_motor_direction = -1;
        auto_intake = false;
    }

    public void manualStop() {
        current_motor_direction = 0;
        auto_intake = false;
    }
    public void autoIntake() {
        auto_intake = true;
    }
    

    @Override
    public void periodic() {

        if (current_motor_direction != 0 || auto_intake) {
            current_motor_power = current_motor_direction * Math.max(
                current_motor_power + kP*(target_motor_velocity-Math.abs(m_motor.getEncoder().getVelocity())),
                min_motor_power
            ); 

        } else {
            current_motor_power=0;
        }

        // put auto code here later because I forgot how it works
        if (false) {
            auto_intake = false;
        }

        m_motor.set(current_motor_power);
    }
    
}
