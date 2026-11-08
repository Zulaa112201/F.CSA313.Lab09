package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ScholarshipCalculatorTest {

    private final ScholarshipCalculator calc = new ScholarshipCalculator();

    

    @Test
    void letterGradeA() {
        assertEquals("A", calc.letterGrade(95));
    }

    @Test
    void letterGradeB() {
        assertEquals("B", calc.letterGrade(85));
    }

    @Test
    void gpaOfTwoGrades() {
        assertEquals(3.5, calc.gpa(new double[]{95, 85}), 1e-9);
    }

    @Test
    void scholarshipFull() {
        assertEquals(100, calc.scholarship(3.9, 16, false));
    }

    @Test
    void scholarshipHalf() {
        assertEquals(50, calc.scholarship(3.1, 10, false));
    }



    @Test
    void letterGradeC() {
        assertEquals("C", calc.letterGrade(75));
    }

    @Test
    void letterGradeD() {
        assertEquals("D", calc.letterGrade(65));
    }

    @Test
    void letterGradeF() {
        assertEquals("F", calc.letterGrade(50));
    }

    @Test
    void letterGradeNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1));
    }

    @Test
    void letterGradeAboveHundredThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101));
    }

    
    @Test
    void gpaNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
    }

    @Test
    void gpaEmptyThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[]{}));
    }

    @Test
    void gpaAllGradeLevels() {
        
        assertEquals(2.0, calc.gpa(new double[]{92, 82, 72, 62, 30}), 1e-9);
    }


    @Test
    void scholarshipNegativeGpaThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(-0.1, 10, false));
    }

    @Test
    void scholarshipGpaAboveFourThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(4.1, 10, false));
    }

    @Test
    void scholarshipNegativeCreditsThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(3.5, -1, false));
    }

    @Test
    void scholarshipViolationReturnsZero() {
        assertEquals(0, calc.scholarship(3.9, 16, true));
    }

    @Test
    void scholarshipHighGpaLowCreditsGets70() {
        
        assertEquals(70, calc.scholarship(3.9, 14, false));
    }

    @Test
    void scholarshipSeventy() {
        assertEquals(70, calc.scholarship(3.5, 12, false));
    }

    @Test
    void scholarshipMidGpaLowCreditsGets50() {
        
        assertEquals(50, calc.scholarship(3.5, 10, false));
    }

    @Test
    void scholarshipLowGpaReturnsZero() {
        assertEquals(0, calc.scholarship(2.5, 15, false));
    }
    @Test
    void gpaSingleGrade() {
        assertEquals(4.0, calc.gpa(new double[]{95}), 1e-9);
    }

    @Test
    void gpaManyGrades() {
        assertEquals(2.5, calc.gpa(new double[]{95, 85, 75, 65}), 1e-9);
    }
}