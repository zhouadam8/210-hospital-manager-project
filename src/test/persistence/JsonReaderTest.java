package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//test suite for JsonReader
class JsonReaderTest {
    ArrayList<Doctor> doctors = new ArrayList<>();
    ArrayList<Patient> patients = new ArrayList<>();
    int id = 0;

    //test when file doesnt exist
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ArrayList<Object> read = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    //test for empty file
    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyHospital.json");
        try {
            ArrayList<Object> test = reader.read();
            assertEquals(3, test.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}