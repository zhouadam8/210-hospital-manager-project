package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.print.Doc;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {
    Patient testPatient;
    Doctor testDoctor;
    @BeforeEach
    void runBefore() {
        testPatient = new Patient("Tim", 145, 22, 1);
        testDoctor = new Doctor("Tin Tin");
    }

    @Test
    void testConstructor() {
        assertEquals("Tim", testPatient.getName());
        assertEquals(145, testPatient.getHeight());
        assertEquals(22, testPatient.getWeight());
        assertEquals(0, testPatient.getHistory().size());
        assertFalse(testPatient.getHospitalized());
        assertEquals("", testPatient.getDoctor());
    }

    @Test
    void testHospitalized() {
        testPatient.newHospitalized("02/10/2022", "some random reason", testDoctor);
        assertTrue(testPatient.getHospitalized());
        assertEquals("Tin Tin", testPatient.getDoctor());
    }

    @Test
    void testCured() {
        testPatient.newHospitalized("02/10/2022", "some random reason", testDoctor);
        testPatient.cured("02/19/2022");
        assertFalse(testPatient.getHospitalized());
        assertEquals(null, testPatient.getDoctor());
        assertEquals(1, testPatient.getHistory().size());
    }

    @Test
    void testSetters() {
        testPatient.setHeight(100);
        assertEquals(22, testPatient.getWeight());
        assertEquals(100, testPatient.getHeight());
        testPatient.setWeight(50);
        assertEquals(100, testPatient.getHeight());
        assertEquals(50, testPatient.getWeight());
    }

    @Test
    void testCondText() {
        assertEquals(" - Not Admitted", testPatient.condText());
        testPatient.newHospitalized("02/10/2022", "some random reason", testDoctor);
        assertEquals(" - Admitted", testPatient.condText());
    }

    @Test
    void testAddMedStatus() {
        MedStatus testmed1 = new MedStatus("12/03/2022", "athma", "testdoc1");
        MedStatus testmed2 = new MedStatus("22/11/2023", "good reason", "testdoc2");
        assertEquals(0, testPatient.getHistory().size());
        testPatient.addHistory(testmed1);
        testPatient.addHistory(testmed2);
        assertEquals(2, testPatient.getHistory().size());
    }
}
