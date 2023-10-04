package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

import javax.print.Doc;

// Class representation for a JSON Reader
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    public ArrayList<Object> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArrayDoc = jsonObject.getJSONArray("doctors");
        JSONArray jsonArrayPat = jsonObject.getJSONArray("patients");
        ArrayList<Doctor> docs = new ArrayList<>();
        ArrayList<Patient> pats = new ArrayList<>();
        ArrayList<Object> rsf = new ArrayList<>();

        for (Object json : jsonArrayDoc) {
            JSONObject nextDoc = (JSONObject) json;
            Doctor doc = parseDoctor(nextDoc);
            docs.add(doc);
        }

        for (Object json : jsonArrayPat) {
            JSONObject nextPat = (JSONObject) json;
            Patient pat = parsePatient(nextPat);
            pats.add(pat);
        }

        int id = jsonObject.getInt("id");
        rsf.add(docs);
        rsf.add(pats);
        rsf.add(id);

        return rsf;
    }

    // EFFECT: constructs doctor from jsonObject
    private Doctor parseDoctor(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Doctor doc = new Doctor(name);
        addPatients(doc, jsonObject);
        return doc;
    }

    // EFFECT: adds the patients in the jsonObject to the doctor
    private void addPatients(Doctor doctor, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("patients");
        for (Object json : jsonArray) {
            JSONObject nextPatient = (JSONObject) json;
            Patient patient = parsePatient(nextPatient);
            doctor.addPatient(patient);
        }
    }

    // EFFECT: constructs a patient from jsonObject
    private Patient parsePatient(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int height = jsonObject.getInt("height");
        int weight = jsonObject.getInt("weight");
        boolean status = jsonObject.getBoolean("hospitalized");
        String doctor = jsonObject.getString("doctor");
        JSONObject jsonIllness = jsonObject.getJSONObject("illness");;
        MedStatus illness = parseMedStatus(jsonIllness);
        int id = jsonObject.getInt("id");
        Patient patient = new Patient(name, height, weight, id);
        addHistory(patient, jsonObject);
        patient.setDoctor(doctor);
        patient.setHospitalized(status);
        patient.setIllness(illness);
        return patient;
    }

    // EFFECT: adds the illness history from jsonObject to patient
    private void addHistory(Patient patient, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("history");
        for (Object json : jsonArray) {
            JSONObject nextHistory = (JSONObject) json;
            MedStatus history = parseMedStatus(nextHistory);
            patient.addHistory(history);
        }
    }

    // EFFECT: constructs a MedStatus from jsonObject
    private MedStatus parseMedStatus(JSONObject jsonObject) {
        String inDate = jsonObject.getString("initialDate");
        String finDate = jsonObject.getString("finalDate");
        String reason = jsonObject.getString("reason");
        String doctor = jsonObject.getString("doctor");
        MedStatus ms = new MedStatus(inDate, reason, doctor);
        ms.setFinalDate(finDate);
        return ms;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

}
