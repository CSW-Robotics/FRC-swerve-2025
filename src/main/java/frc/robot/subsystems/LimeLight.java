
package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

// don't use venderdrop, use this insted: 
// https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/ADIS16448_IMU.html


public class LimeLight extends SubsystemBase {

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    NetworkTableEntry tid = table.getEntry("tid");
    NetworkTableEntry getpipe = table.getEntry("getpipe");

    protected double x;
    protected double y;
    protected double area;
    protected double id;
    protected double pipe;

    public void setAprilTag() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    }

    public void setReflective() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);
        double id = tid.getDouble(0.0);
        double pipe = getpipe.getDouble(0.0);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }
}