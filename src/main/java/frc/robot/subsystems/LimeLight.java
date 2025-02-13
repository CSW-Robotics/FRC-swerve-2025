
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.lang.reflect.Array;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight extends SubsystemBase {

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry entry_tx = table.getEntry("tx");
    NetworkTableEntry entry_ty = table.getEntry("ty");
    NetworkTableEntry entry_ta = table.getEntry("ta");
    NetworkTableEntry entry_tid = table.getEntry("tid");
    NetworkTableEntry entry_getpipe = table.getEntry("getpipe");
    
    // general AT data
    public double tid;
    public double getpipe;

    // 2d AT data
    public double tx;
    public double ty;
    public double ta;

    // 3d AT data
    public double[] DDDx3_data3D = NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(new double[6]);

    public void setAprilTag() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    }

    public void setReflective() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        double tx = entry_tx.getDouble(0.0);
        double ty = entry_ty.getDouble(0.0);
        double area = entry_ta.getDouble(0.0);
        double id = entry_tid.getDouble(0.0);
        double getpipe = entry_getpipe.getDouble(0.0);

        DDDx3_data3D = NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
        for (int i=0; i<6; i++) {
            System.out.print(DDDx3_data3D[i] + ", ");
        }
        System.out.println(" ");
        
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation
    }
}