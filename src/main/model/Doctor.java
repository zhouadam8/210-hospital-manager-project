package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Class representation of a doctor: with name and patients currently managed by the doctor
public class Doctor {
    private String name;
    private List<Patient> patients;

    // REQUIRES: name has non-zero length
    // EFFECTS: creates a new doctor with name and no patients
    public Doctor(String name) {
        this.name = name;
        this.patients = new ArrayList<>();
    }

    // REQUIRES: patient is in patients
    // MODIFIES: this
    // EFFECTS: removes the patient from patients
    public void removePatient(Patient patient) {
        int id = patient.getId();
        Patient removep = null;
        for (Patient p : patients) {
            if (p.getId() == id) {
                removep = p;
            }
        }
        patients.remove(removep);
    }

    public String getName() {
        return name;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    // MODIFIES: this
    // EFFECTS: adds patient to the patients under this doctor
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    // EFFECT: returns the doctor is a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("patients", patientsToJson());
        return json;
    }

    // EFFECT: returns the patients managed by the doctor as a JSONArray
    private JSONArray patientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Patient p: patients) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

}
