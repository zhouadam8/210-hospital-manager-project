package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorTest {
    private Doctor testDoc;
    private Patient p1;
    private Patient p2;
    private Patient p3;

    @BeforeEach
    void runBefore() {
        p1 = new Patient("Timmy", 157, 65, 1);
        p2 = new Patient("Tammy", 140, 55, 2);
        p3 = new Patient("Timmy", 55, 20, 3);
        testDoc = new Doctor("Tommy Lee");
    }

    @Test
    void testConstructor() {
        assertEquals("Tommy Lee", testDoc.getName());
        assertEquals(0, testDoc.getPatients().size());
    }

    @Test
    void testAddPatient() {
        testDoc.addPatient(p1);
        assertEquals(1, testDoc.getPatients().size());
        testDoc.addPatient(p2);
        testDoc.addPatient(p3);
        assertEquals(3, testDoc.getPatients().size());
    }

    @Test
    void testRemovePatient() {
        testDoc.addPatient(p1);
        testDoc.addPatient(p2);
        testDoc.addPatient(p3);
        assertEquals(3, testDoc.getPatients().size());
        testDoc.removePatient(p1);
        assertTrue(testDoc.getPatients().contains(p2));
        assertTrue(testDoc.getPatients().contains(p3));
        testDoc.removePatient(p2);
        assertTrue(testDoc.getPatients().contains(p3));
        testDoc.removePatient(p3);
        assertEquals(0, testDoc.getPatients().size());
    }
}
