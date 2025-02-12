package frc.robot.subsystems;

import static edu.wpi.first.units.Units.derive;

import org.dyn4j.collision.narrowphase.DistanceDetector;
import com.revrobotics.sim.SparkLimitSwitchSim;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.LimitSwitchConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation.MatchType;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Elevator extends SubsystemBase {
    // SubsystemBase being an imported class

    // Define the motors which will move the elevator. 
    SparkMax motor1 = new SparkMax(0, MotorType.kBrushless);
    SparkMax motor2 = new SparkMax(1, MotorType.kBrushless);
    
    // DititalInput.get returns true or false for each limit switch on and off respectively
    // Define 4 limit switches to start, as adding all 8 might be too complex
    DigitalInput limitSwitch1 = new DigitalInput(2); // do we need to change the channel for each limit switch? 
    DigitalInput limitSwitch2 = new DigitalInput(2);
    DigitalInput limitSwitch3 = new DigitalInput(2);
    DigitalInput limitSwitch4 = new DigitalInput(2);

    // integer type variables for later use 
    protected int currentStage = 0;
    protected int desiredStage = 0;

    public void ChangeDesiredStage(int newDesiredStage) {
        // Set my desiredStage to the newDesiredStage to remember our new target
        desiredStage = newDesiredStage;    
    }

    

    public int Direction(){
        // Gives us the current direction based off of the desired stage
            if (currentStage > desiredStage){
                return -1; // Go down
            }
            else if (currentStage < desiredStage){
                return 1; // Go up
            }
            else{
                return 0; //stay in place 
            }
    }
    
   
    public void MoveTo(){
            // Move to the desired stage by adjusting the motor speeds given the direction

            // we multiply the motor starting speed by direction because direction is either 1,-1, or 0 
            // afterwards, divide by 4 - the absolute value of the difference of the stages
            // it travels faster when farther away, and slower when the difference approaches 0 
            //double motorSpeed = (0.08*Direction())/(4-Math.abs(currentStage-desiredStage));
            double motorSpeed = 0.02*Direction(); // store constant speed in appropriate direction, avoiding a difference in motor speeds
            motor1.set(motorSpeed);
            motor2.set(motorSpeed);
 

    }

    @Override
    public void periodic(){
        // This funcion should run every 20 MSEC
        // 1. Check whether the sensors have been activated to see if we need to update currentStage
        boolean limitSwitch0Input = limitSwitch1.get();
        boolean limitSwitch1Input = limitSwitch2.get();
        boolean limitSwitch2Input = limitSwitch3.get();
        boolean limitSwitch3Input = limitSwitch4.get();
        
        if (limitSwitch0Input == true){
            // The elevator is at ...
            // stage 0
        }
        else if (limitSwitch1Input == true){
            // stage 1
        }
        else if (limitSwitch2Input == true){
            // stage 2
        }
        else if (limitSwitch3Input == true){
            // stage 3
        }
        else {
            // The elevator is between limit switches or an error has occured
            System.out.println("The elevator is between limit switches or an error has occured");
        }



        // 2. Run moveTo function that just sets motor speed in the correct direction
        //    towards our destination
        MoveTo();
        
    }

    @Override
    public void simulationPeriodic() {
    }

}