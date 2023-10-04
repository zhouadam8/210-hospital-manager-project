package model;

import model.MedStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Class Representation of a Patient with name, height, weight, the doctor managing, past illness, current, illness
// whether or not the patient is currently in the hospital, and a unique id
public class Patient {
    private String name;
    private int height;
    private int weight;
    private String doctor;
    private List<MedStatus> history;
    private MedStatus illness;
    private Boolean hospitalized;
    private int id;

    // REQUIRES: name has a non-zero length, int and weight > 0
    // EFFECTS: creates a Patient with name height and weight, no illness and no doctor and not hospitalized
    public Patient(String name, int height, int weight, int id) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.history = new ArrayList<>();
        this.illness = new MedStatus("", "", "");
        this.doctor = "";
        this.hospitalized = false;
        this.id = id;
    }

    // REQUIRES: initialDate and reason has non-zero length
    // MODIFIES: this
    // EFFECTS: adds the current illness and changes status to hospitalized and adds the current doctor
    public void newHospitalized(String initialDate, String reason, Doctor doctor) {
        this.illness = new MedStatus(initialDate, reason, doctor.getName());
        this.hospitalized = true;
        this.doctor = doctor.getName();
    }

    // REQUIRES: finalDate has non-zero length
    // MODIFIES: this
    // EFFECTS: changes status to not hospitalized,
    // removes the current illness and adds it to the illness history and also removes the doctor
    public void cured(String finalDate) {
        illness.discharged(finalDate);
        history.add(illness);
        this.illness = new MedStatus("", "", "");
        this.hospitalized = false;
        this.doctor = "";
    }

    // REQUIRES: weight >0
    // MODIFIES: this
    // Effect: changes patient weight to weight
    public void setWeight(int weight) {
        this.weight = weight;
    }

    // Effect: returns if the patient is hospitalized or not
    public String condText() {
        if (this.hospitalized) {
            return " - Admitted";
        } else {
            return " - Not Admitted";
        }
    }

    // REQUIRES: height >0
    // MODIFIES: this
    // Effect: changes patient height to height
    public void setHeight(int height) {
        this.height = height;
    }

    // MODIFIES: this
    // EFFECT: adds the MedStatus onto patient history
    public void addHistory(MedStatus medStatus) {
        this.history.add(medStatus);
    }

    public void setHospitalized(boolean hospitalized) {
        this.hospitalized = hospitalized;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setIllness(MedStatus illness) {
        this.illness = illness;
    }

    public List<MedStatus> getHistory() {
        return history;
    }

    public String getName() {
        return name;
    }

    public boolean getHospitalized() {
        return hospitalized;
    }

    public String getDoctor() {
        return doctor;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }

    // EFFECT: converts patient to a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("height", height);
        json.put("weight", weight);
        json.put("doctor", doctor);
        json.put("history", historyToJson());
        json.put("illness", illness.toJson());
        json.put("hospitalized", hospitalized);
        json.put("id", id);
        return json;
    }

    // EFFECT: converts the JSONObjects of the illness in the history into a JSONArray
    private JSONArray historyToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MedStatus m: history) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }
}
