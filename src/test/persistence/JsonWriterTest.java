package persistence;

import model.Doctor;
import model.MedStatus;
import model.Patient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Test suite for JsonWriter
class JsonWriterTest {

    //Test for the destination for file is invalid
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    //Test for writing and reading empty file
    @Test
    void testWriterEmptyHosptial() {
        try {
            ArrayList<Object> test1 = new ArrayList<>();
            ArrayList<Doctor> doctors = new ArrayList<>();
            ArrayList<Patient> patients = new ArrayList<>();
            int id = 0;
            test1.add(doctors);
            test1.add(patients);
            test1.add(id);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHospital.json");
            writer.open();
            writer.write(doctors, patients, id);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyHospital.json");
            ArrayList<Object> testr = reader.read();
            ArrayList<Doctor> r1 = (ArrayList<Doctor>) testr.get(0);
            ArrayList<Patient> r2 = (ArrayList<Patient>) testr.get(1);
            int testid = (int) testr.get(2);
            assertEquals(0, r1.size());
            assertEquals(0, r2.size());
            assertEquals(0,testid);
            assertEquals(3, testr.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    //Test for writing and reading example hospital
    @Test
    void testWriterGeneralHospital() {
        try {
            ArrayList<Object> test2 = new ArrayList<>();
            ArrayList<Doctor> doctors = new ArrayList<>();
            ArrayList<Patient> patients = new ArrayList<>();
            Doctor testdoc1 = new Doctor("tim");
            Doctor testdoc2 = new Doctor("thom");
            Patient testpat1 = new Patient("tes",160,45, 0);
            Patient testpat2 = new Patient("cruz", 180, 65, 1);
            Patient testpat3 = new Patient("crum", 195, 200, 2);
            MedStatus testmed1 = new MedStatus("12/03/2022", "athma", "testdoc1");
            MedStatus testmed2 = new MedStatus("22/11/2023", "good reason", "testdoc2");

            testdoc1.addPatient(testpat1);
            testdoc1.addPatient(testpat3);

            doctors.add(testdoc1);
            doctors.add(testdoc2);

            testpat1.addHistory(testmed1);
            testpat2.addHistory(testmed2);
            patients.add(testpat1);
            patients.add(testpat2);
            patients.add(testpat3);
            int id = 3;
            test2.add(doctors);
            test2.add(patients);
            test2.add(id);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHosptial.json");
            writer.open();
            writer.write(doctors, patients, id);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralHosptial.json");
            ArrayList<Object> testr = reader.read();
            ArrayList<Doctor> r1 = (ArrayList<Doctor>) testr.get(0);
            ArrayList<Patient> r2 = (ArrayList<Patient>) testr.get(1);
            int testid = (int) testr.get(2);
            assertEquals(2, r1.size());
            assertEquals(3, r2.size());
            assertEquals(3,testid);
            assertEquals(3, testr.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}