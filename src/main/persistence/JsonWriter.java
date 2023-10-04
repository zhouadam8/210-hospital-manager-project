package persistence;


import model.Doctor;
import model.Patient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

// Class for Writing JSON
public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of list fo doctors, patients and id to file
    public void write(ArrayList<Doctor> docs, ArrayList<Patient> patients, int id) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonDoc = new JSONArray();
        JSONArray jsonPat = new JSONArray();

        for (Doctor d : docs) {
            jsonDoc.put(d.toJson());
        }

        for (Patient p : patients) {
            jsonPat.put(p.toJson());
        }

        jsonObject.put("patients", jsonPat);

        jsonObject.put("doctors", jsonDoc);

        jsonObject.put("id", id);

        saveToFile(jsonObject.toString(4));
    }
}
