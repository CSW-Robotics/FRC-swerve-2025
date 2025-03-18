package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase;

public class AlgaeRemover extends SubsystemBase {

    private SparkMax motor = new SparkMax(10, MotorType.kBrushless);
    private SparkMaxConfig motorConfig = new SparkMaxConfig();

    public AlgaeRemover() {
      motorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
      motor.configure(motorConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    }

    public void SetMotor(double speed) {
      motor.set(speed);
    }

    
}