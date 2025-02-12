package frc.robot.subsystems;

import static edu.wpi.first.units.Units.derive;

import org.dyn4j.collision.narrowphase.DistanceDetector;
import com.revrobotics.sim.SparkLimitSwitchSim;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.LimitSwitchConfig;

import edu.wpi.first.wpilibj.DigitalInput;

// print("testing commit")
public class Elevator {
    
    SparkMax motor1 = new SparkMax(0, null);
    SparkMax motor2 = new SparkMax(1, null);
    
    // DititalInput.get returns true or false for on and off
    DigitalInput limitSwitch = new DigitalInput(2);
     //parkLimitSwitchSim limits = new SparkLimitSwitchSim(motor2, false);
  
    int CurrentStage = 0;
    int DesiredStage;

    // Tells what stage the elevator is at
    //MAKE STRING LATER FOR READABILLITY
    public String Direction(DigitalInput i){
            if (CurrentStage > DesiredStage){
                return "DOWN";
            }
            else if (CurrentStage < DesiredStage){
                return "UP";
            }
            else{
                return "THERE";
            }
    }
    // Returns what stage the robot was most recently at
    public int Stage(){
        if (limitSwitch.get() && Direction(limitSwitch) == "DOWN"){
            CurrentStage = CurrentStage - 1;
        
        }
        else if (limitSwitch.get() && Direction(limitSwitch) == "UP"){
            CurrentStage = CurrentStage + 1;
        
        }
        else if (limitSwitch.get() && Direction(limitSwitch) == "THERE"){
            CurrentStage = CurrentStage;
        }
        else{
            // add somthing here
        }
        return CurrentStage;
    }

    public void MoveTo(){
        if (Direction(limitSwitch) == "UP"){
            //make p later
            motor1.set(.3);
            motor2.set(.3);
        }
        else if (Direction(limitSwitch) == "DOWN"){
            motor1.set(-0.3);
            motor2.set(-0.3);
        }
        else if (Direction(limitSwitch) == "THERE"){
            //change to brake mode when we find out
            motor1.set(0);
            motor2.set(0);
        }
    }
}




