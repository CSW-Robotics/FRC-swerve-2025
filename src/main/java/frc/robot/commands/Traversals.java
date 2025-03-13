package frc.robot.commands;

import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class Traversals extends Command {

    public static Command In(SwerveSubsystem drivebase, LimeLight limeLight, double end_processor){
        // Path should look like "IN-T19-S1"
        return drivebase.getAutonomousCommand("IN-T"+limeLight.last_seen_tag_id+"-S"+end_processor);
    }
    
    public static Command Out(SwerveSubsystem drivebase, double from_processor, boolean clockwise){
        // Path should look like "OUT-S1-T19"
        return drivebase.getAutonomousCommand("OUT-T"+from_processor+"-CW");
    }
    
}
