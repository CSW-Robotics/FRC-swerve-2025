package frc.robot.subsystems;

import static edu.wpi.first.units.Units.derive;

import org.dyn4j.collision.narrowphase.DistanceDetector;
import com.revrobotics.sim.SparkLimitSwitchSim;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.LimitSwitchConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;


public class Elevator {
    
    SparkMax motor1 = new SparkMax(0, null);
    SparkMax motor2 = new SparkMax(1, null);
    
    // DititalInput.get returns true or false for on and off
    DigitalInput limitSwitch = new DigitalInput(2);

    int CurrentStage = 0;
    int DesiredStage;
    boolean PrevousSwitch = false;
    double P = 0.03;

    public Elevator(){

    }

    // we run this and moveTo in robot periodic or something along those lines and then we just need to bind desired height and
    // it should work for the most part
    // here again we dont need the if statements as because Direction is 1,-1,0 the math works out
    public void CheckSwitch() {

        // everytime this switch changes to true from false it triggers this code to change the sector
        if (PrevousSwitch != limitSwitch.get() && PrevousSwitch != true) {

            CurrentStage = CurrentStage + Direction();

            PrevousSwitch = limitSwitch.get();

        }

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
    
   
    // sets the motors to the right speed because direction is 1,-1,0 this works out
    public void MoveTo(){
        
            //make p later
            motor1.set(P*Direction());
            motor2.set(P*Direction());
    }

    public void ElevatorPeriodic() {

        this.CheckSwitch();

        this.MoveTo();
    }

}




