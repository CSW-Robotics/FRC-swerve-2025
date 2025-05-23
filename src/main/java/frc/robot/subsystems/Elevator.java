package frc.robot.subsystems;

import static edu.wpi.first.units.Units.derive;

import org.dyn4j.collision.narrowphase.DistanceDetector;

// import com.revrobotics.Rev2mDistanceSensor;
// import com.revrobotics.Rev2mDistanceSensor.Port;
// import com.revrobotics.Rev2mDistanceSensor.Unit;
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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

// Class declaration and inheritance
public class Elevator extends SubsystemBase {

    // Define the motors which will move the elevator; James and I were unsure of the specific 
    // motor syntax. We also don't know the id's of the motors manning the elevator 
    SparkMax motor1 = new SparkMax(9, MotorType.kBrushless);
    // One of them will need to be inverted.
    SparkMax motor2 = new SparkMax(13, MotorType.kBrushless);
    
    // We only need one motor configuration, as we can set both motors to the same configuration
    SparkMaxConfig motorConfig = new SparkMaxConfig();

    public Elevator(){
        // A constructor that runs whenever an object of Elevator class is built
        
        // Set both motors to a brake mode so that even when set to a speed of zero the elevator
        // stays in place
        motorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);

        // Line wrapping
        motor1.configure(motorConfig, SparkBase.ResetMode.kResetSafeParameters, 
                         SparkBase.PersistMode.kPersistParameters);

        motorConfig.inverted(true);
        motor2.configure(motorConfig, SparkBase.ResetMode.kResetSafeParameters, 
                         SparkBase.PersistMode.kPersistParameters);

    
    }
    
    // DititalInput.get returns true or false for each limit switch on and off respectively
    // Define 4 limit switches to start, as adding all 8 might be too complex
    DigitalInput limitSwitch0 = new DigitalInput(0);
    DigitalInput limitSwitch1 = new DigitalInput(1);
    DigitalInput limitSwitch2 = new DigitalInput(2);
    DigitalInput limitSwitch3 = new DigitalInput(3);

    

    // integer type variables for later use which should not be changeable outside of this class
    public int currentStage = 0;
    public int targetStage = 0;

    boolean ShouldMoveAutomatically = true;


    public void ChangeTargetStage(int newtargetStage) {
        RestartAutoMovement();
        // Set targetStage to the newtargetStage to remember our new target: This target will be 
        // passed in when calling this method 
        targetStage = newtargetStage;  
    }

    public void ChangeTargetStageFromChooser(SendableChooser<Integer> autoChooser) {
        RestartAutoMovement();
        // Set targetStage to the newtargetStage to remember our new target: This target will be 
        // passed in when calling this method 
        targetStage = autoChooser.getSelected();    
    }

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


    public double Direction(){
        // Gives us the current direction based off of the target stage
            if (currentStage > targetStage){
                return -0.7; // Go down
            }
            else if (currentStage < targetStage){
                return 1; // Go up
            }
            else{
                return 0.03; //stay in place 
            }
    }
    
   
    public void MoveTo(){
            
            if (ShouldMoveAutomatically == true) {

                double motorSpeed = Math.max((0.5*(0.5*Math.abs(currentStage-targetStage))), 1.0)*Direction(); // store constant speed in appropriate direction, avoiding a difference in motor speeds

                motor1.set(motorSpeed);
                motor2.set(motorSpeed);
            }

    }

    public void CheckStage(){

        // This funcion should run every 20 MSEC
        // store the input of each limit switch using boolean variables
        boolean limitSwitch0Input = limitSwitch0.get();
        boolean limitSwitch1Input = limitSwitch1.get();
        boolean limitSwitch2Input = limitSwitch2.get();
        boolean limitSwitch3Input = limitSwitch3.get();
        
        boolean activeValue = false;        // the limit switch outputs false when it detects the magnet

        if (limitSwitch0Input == activeValue){
            // The elevator is at stage 1
            currentStage = 0;
        }

        else if (limitSwitch1Input == activeValue){
            // The elevator is at stage 1
            currentStage = 1;
        }
        else if (limitSwitch2Input == activeValue){
            // The elevator is at stage 2
            currentStage = 2;
        }
        else if (limitSwitch3Input == activeValue){
            // The elevator is at stage 3
            currentStage = 3;
        }
    }

    @Override
    public void periodic(){

        CheckStage();
        MoveTo();
        
    }

    @Override
    public void simulationPeriodic() {
        // Not too sure what this does right now. We can ask Nicholas or Proshanto.  
    }

}