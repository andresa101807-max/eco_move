package co.edu.umanizales.eco_move.model.interfaces;

import java.util.Map;

public interface IoTEnabled {
    Map<String, Object> getSensorData();
    void updateSensorData(String sensorName, Object value);
    boolean isSensorActive(String sensorName);
}
