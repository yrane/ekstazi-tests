import static org.junit.Assert.*;

import org.junit.Test;


public class IdentityTest {

    // Statement Coverage
    @Test
    public void testAllTrue() {
        assertEquals(0, CoverageExample.identity(0, true, true, true));
    }
    
    // Branch Coverage, all of the above plus the following
    @Test
    public void testAllFalse() {
        assertEquals(0, CoverageExample.identity(0, false, false, false));
    }

    // Path Coverage, all of the above plus the following
    @Test 
    public void testTrueTrueFalse() {
        assertEquals(0, CoverageExample.identity(0, true, true, false));
    }
    
    @Test 
    public void testTrueFalseTrue() {
        assertEquals(0, CoverageExample.identity(0, true, false, true));
    }
    
    @Test 
    public void testTrueFalseFalse() {
        assertEquals(0, CoverageExample.identity(0, true, false, false));
    }
    
    @Test 
    public void testFalseTrueTrue() {
        assertEquals(0, CoverageExample.identity(0, false, true, true));
    }
    
    @Test 
    public void testFalseTrueFalse() {
        assertEquals(0, CoverageExample.identity(0, false, true, false));
    }

    @Test 
    public void testFalseFalseTrue() {
        assertEquals(0, CoverageExample.identity(0, false, false, true));
    }

}
