package frc.robot.subsystems;

import static edu.wpi.first.units.Units.derive;

import org.dyn4j.collision.narrowphase.DistanceDetector;
import com.revrobotics.sim.SparkLimitSwitchSim;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.LimitSwitchConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation.MatchType;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

// example commit - Ezequiel
public class Elevator extends SubsystemBase {
    
    SparkMax motor1 = new SparkMax(0, MotorType.kBrushless);
    SparkMax motor2 = new SparkMax(1, MotorType.kBrushless);
    
    // DititalInput.get returns true or false for on and off
    DigitalInput limitSwitch = new DigitalInput(2);

    int CurrentStage = 0;
    int DesiredStage = 0;
    boolean ShouldMoveAutomatically = true;
    boolean PrevousSwitchState = false;
    private SparkMaxConfig spark_config = new SparkMaxConfig();

    public Elevator(){
        spark_config.idleMode(SparkBaseConfig.IdleMode.kBrake);
        motor1.configure(spark_config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
        motor2.configure(spark_config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    }

    public void CheckCurrentStage() {

        // when the limit switch changes to true from false we add to the current stage
        if (limitSwitch.get() == true && limitSwitch.get() != PrevousSwitchState) {
            // Because we have made direction 1,-1,0 we dont need complicated if statements we can just add the direction
            CurrentStage = CurrentStage + Direction();
        }

        // sets the prevous switch state to the current state
        PrevousSwitchState = limitSwitch.get();

    }

    

    // Gives us the current direction based off of the desired stage
    public int Direction(){
            if (CurrentStage > DesiredStage){
                return -1;
            }
            else if (CurrentStage < DesiredStage){
                return 1;
            }
            else{
                return 0;
            }
    }
    
   
    public void MoveTo(){

        // we use this to overide the automatic movement
        if (ShouldMoveAutomatically == true) {

            // we multiply the motor starting speed by direction because direction is 1,-1,0 this works out

            // after that we then divide by 4 - the absolute value of the difference of the stages
            // this means it travels faster when it is farther away and when the difference approaches 0 the motor
            // speed approaches 0.02.
            // this gives us a kind of sudo PiD without the use of a distance sensor.
            motor1.set((0.08*Direction())/(4-Math.abs(CurrentStage-DesiredStage)));
            motor2.set((0.08*Direction())/(4-Math.abs(CurrentStage-DesiredStage)));
 
        }
    }


    // a function to just set the motors that we will use to overide the automatic algorithm,
    public void SetMotor(double speed){

        // stops the automatic movement
        ShouldMoveAutomatically = false;

        // sets the speed to the givin speed
        motor1.set(speed);
        motor2.set(speed);

    }

    public void RestartAutoMovement() {
        ShouldMoveAutomatically = true;
    }

    @Override
    public void periodic(){
        MoveTo();
        CheckCurrentStage();
    }

    @Override
    public void simulationPeriodic() {
    }

}