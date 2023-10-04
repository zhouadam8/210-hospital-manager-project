package model;

import model.Doctor;
import org.json.JSONObject;

import javax.print.Doc;

// Class representation of a Hospital Record with date admitted, date released, and doctor
public class MedStatus {
    private String initialDate;
    private String finalDate;
    private String reason;
    private String doctor;

    // REQUIRES: initialDate, reason has non-zero length
    // MODIFIES: this
    // EFFECTS: creates a new MedStatus with initial date, reason, doctor, and empty final date.
    public MedStatus(String initialDate, String reason, String doctor) {
        this.initialDate = initialDate;
        this.finalDate = "";
        this.reason = reason;
        this.doctor = doctor;
    }

    // REQUIRES: finalDate has non-zero length
    // MODIFIES: this
    // EFFECTS: adds a final date
    public void discharged(String finalDate) {
        this.finalDate = finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getReason() {
        return reason;
    }


    // EFFECT: returns the MedStatus as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("initialDate", initialDate);
        json.put("finalDate", finalDate);
        json.put("doctor", doctor);
        json.put("reason", reason);
        return json;
    }
}