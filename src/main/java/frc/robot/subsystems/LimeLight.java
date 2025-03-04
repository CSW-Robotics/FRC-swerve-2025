
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight extends SubsystemBase {

    private String m_network_table_key = "limelight";

    public LimeLight(String network_table_key) {
        m_network_table_key = network_table_key;
    }

    NetworkTable table = NetworkTableInstance.getDefault().getTable(m_network_table_key);
    NetworkTableEntry entry_tx = table.getEntry("tx");
    NetworkTableEntry entry_ty = table.getEntry("ty");
    NetworkTableEntry entry_ta = table.getEntry("ta");
    NetworkTableEntry entry_tid = table.getEntry("tid");
    NetworkTableEntry entry_getpipe = table.getEntry("getpipe");
    
    // general AT data
    public double tid = 0;
    public double getpipe;

    // 2d AT data
    public double tx = 0;
    public double ty = 0;
    public double ta = 0;

    // 3d AT data
    public double[] DDDx3_data3D = NetworkTableInstance.getDefault().getTable(m_network_table_key).getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);

    public void setAprilTag() {
        NetworkTableInstance.getDefault().getTable(m_network_table_key).getEntry("pipeline").setNumber(0);
    }

    public void setReflective() {
        NetworkTableInstance.getDefault().getTable(m_network_table_key).getEntry("pipeline").setNumber(1);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        tx = entry_tx.getDouble(0.0);
        ty = entry_ty.getDouble(0.0);
        ta = entry_ta.getDouble(0.0);
        tid = entry_tid.getDouble(0.0);
        getpipe = entry_getpipe.getDouble(0.0);

        DDDx3_data3D = NetworkTableInstance.getDefault().getTable(m_network_table_key).getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
    }

    @Override
    public void simulationPeriodic() {}
}