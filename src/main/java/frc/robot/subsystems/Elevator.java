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
import edu.wpi.first.wpilibj2.command.button.Trigger;


public class Elevator {
    
    SparkMax motor1 = new SparkMax(0, MotorType.kBrushless);
    SparkMax motor2 = new SparkMax(1, MotorType.kBrushless);
    
    // DititalInput.get returns true or false for on and off
    DigitalInput limitSwitch = new DigitalInput(2);

    int CurrentStage = 0;
    int DesiredStage = 0;
    boolean PrevousSwitch = false;

    public Elevator(){

    }

    // we run this and moveTo in robot periodic or something along those lines and then we just need to set the desired height.
    public void CheckSwitch() {

        // everytime this switch changes to true from false it triggers this code to change the sector/
        if (PrevousSwitch != limitSwitch.get() && PrevousSwitch != true) {

            // Because we have made direction 1,-1,0 we dont need complicated if statements we can just add the direction
            CurrentStage = CurrentStage + Direction();

            // we set the prevous switch to this so that we dont loop this command while the switch is true
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
    
   
    public void MoveTo(){

            // we multiply the motor starting speed by direction because direction is 1,-1,0 this works out

            // after that we then divide by 4 - the absolute value of the difference of the stages
            // this means it travels faster when it is farther away and when the difference approaches 0 the motor
            // speed approaches 0.02.
            // this gives us a kind of sudo PiD without the use of a distance sensor.
            motor1.set((0.08*Direction())/(4-Math.abs(CurrentStage-DesiredStage)));
            motor2.set((0.08*Direction())/(4-Math.abs(CurrentStage-DesiredStage)));
 

    }

    // this is just one function we call that runs all of our check functions
    // I did this because I couldnt figure out how to bind a trigger to when the limit switch is pressed
    public void ElevatorPeriodic() {

        this.CheckSwitch();

        this.MoveTo();
    }

}




