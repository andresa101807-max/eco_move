package co.edu.umanizales.eco_move.model.interfaces;

import co.edu.umanizales.eco_move.model.records.Coordinates;

public interface Autonomous {
    void setDestination(Coordinates destination);
    Coordinates getCurrentPosition();
    boolean isAutoPilotEnabled();
    void enableAutoPilot();
    void disableAutoPilot();
}
