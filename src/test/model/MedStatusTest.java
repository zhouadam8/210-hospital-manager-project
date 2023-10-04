package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedStatusTest {
    private MedStatus test1;
    private Doctor doc1;
    private MedStatus test2;

    @BeforeEach
    void runBefore() {
        doc1 = new Doctor("Bob Frank");
        test1 = new MedStatus("02/10/2023", "some random reason", "Bob Frank");
        test2 = new MedStatus("02/29/2012", "another cool reason", "Bob Frank");
    }

    @Test
    void testConstructor() {
        assertEquals(null, test1.getFinalDate());
        assertEquals("Bob Frank", test1.getDoctor());
        assertEquals("02/10/2023", test1.getInitialDate());
        assertEquals("some random reason", test1.getReason());
        assertEquals(null, test2.getFinalDate());
        assertEquals("Bob Frank", test2.getDoctor());
        assertEquals("02/29/2012", test2.getInitialDate());
        assertEquals("another cool reason", test2.getReason());
    }

    @Test
    void testDischarged() {
        test1.discharged("02/20/2024");
        assertEquals("Bob Frank", test1.getDoctor());
        assertEquals("02/10/2023", test1.getInitialDate());
        assertEquals("some random reason", test1.getReason());
        assertEquals("02/20/2024", test1.getFinalDate());
        test2.discharged("31/07/2020");
        assertEquals("Bob Frank", test2.getDoctor());
        assertEquals("02/29/2012", test2.getInitialDate());
        assertEquals("another cool reason", test2.getReason());
        assertEquals("31/07/2020", test2.getFinalDate());
    }


}