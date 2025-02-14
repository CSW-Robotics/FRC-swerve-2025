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

// Class declaration and inheritance
public class ElevatorWithMultipleSensors extends SubsystemBase {

    // Define the motors which will move the elevator; James and I were unsure of the specific 
    // motor syntax. We also don't know the id's of the motors manning the elevator 
    SparkMax motor1 = new SparkMax(0, MotorType.kBrushless);
    SparkMaxConfig motor1config = new SparkMaxConfig();
    
    SparkMax motor2 = new SparkMax(1, MotorType.kBrushless);
    SparkMaxConfig motor2config = new SparkMaxConfig();

    public ElevatorWithMultipleSensors(){
        // A constructor that runs whenever an object of Elevator class is built
        
        // Set both motors to a brake mode so that even when set to a speed of zero the elevator
        // stays in place
        motor1config.idleMode(SparkBaseConfig.IdleMode.kBrake);
        motor1.configure(motor1config, SparkBase.ResetMode.kResetSafeParameters, 
                         SparkBase.PersistMode.kPersistParameters);

        motor2config.idleMode(SparkBaseConfig.IdleMode.kBrake);
        motor2.configure(motor2config, SparkBase.ResetMode.kResetSafeParameters, 
                         SparkBase.PersistMode.kPersistParameters);
    }
    
    // DititalInput.get returns true or false for each limit switch on and off respectively
    // Define 4 limit switches to start, as adding all 8 might be too complex
    DigitalInput limitSwitch1 = new DigitalInput(0); 
    DigitalInput limitSwitch2 = new DigitalInput(1);
    DigitalInput limitSwitch3 = new DigitalInput(2);
    DigitalInput limitSwitch4 = new DigitalInput(3);

    // integer type variables for later use which should not be changeable outside of this class
    protected int currentStage = 0;
    protected int targetStage = 0;

    public void ChangeTargetStage(int newtargetStage) {
        // Set targetStage to the newtargetStage to remember our new target: This target will be 
        // passed in when calling this method 
        targetStage = newtargetStage;    
    }

    

    public int Direction(){
        // Gives us the current direction based off of the target stage
            if (currentStage > targetStage){
                return -1; // Go down
            }
            else if (currentStage < targetStage){
                return 1; // Go up
            }
            else{
                return 0; //stay in place 
            }
    }
    
   
    public void MoveTo(){
            // Move to the target stage by adjusting the motor speeds given the direction

            // we multiply the motor starting speed by direction because direction is either 1,-1, or 0 
            // afterwards, divide by 4 - the absolute value of the difference of the stages
            // it travels faster when farther away, and slower when the difference approaches 0 
            //double motorSpeed = (0.08*Direction())/(4-Math.abs(currentStage-targetStage));
            double motorSpeed = 0.02*Direction(); // store constant speed in appropriate direction, avoiding a difference in motor speeds
            motor1.set(motorSpeed);
            motor2.set(motorSpeed);
            // Proshanto says it depends on the default brake mode of the motor. It seems that we 
            // want the coast mode as opposed to the brake mode: This may need to happen using the
            // hardware client. 
 

    }

    @Override
    public void periodic(){
        // This funcion should run every 20 MSEC
        // store the input of each limit switch using boolean variables
        boolean limitSwitch0Input = limitSwitch1.get();
        boolean limitSwitch1Input = limitSwitch2.get();
        boolean limitSwitch2Input = limitSwitch3.get();
        boolean limitSwitch3Input = limitSwitch4.get();
        
        boolean activeValue = false;        // the limit switch outputs false when it detects the magnet

        // 1. Check whether the sensors have been activated to see if we need to update currentStage
            // Will the limit switches be regularly open or closed? 
        if (limitSwitch0Input == activeValue){
            // The elevator is at stage 0
            // update currentStage to reflect this
            currentStage = 0; // Is this different from "currentStage = targetStage?"
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
        else {
            // The elevator is between limit switches or an error has occured
            //System.out.println("The elevator is between limit switches or an error has occured");
        }



        // 2. Run moveTo function that just sets motor speed in the correct direction
        //    towards our destination
        MoveTo();
        
    }

    @Override
    public void simulationPeriodic() {
        // Not too sure what this does right now. We can ask Nicholas or Proshanto.  
    }

}