package com.spe.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {



    private final double DELTA = 1e-9; // A small tolerance for double comparisons

    @Test
    @DisplayName("Test Square Root Function")
    void testSquareRoot() {
        assertEquals(5.0, App.squareRoot(25.0), DELTA);
        assertEquals(0.0, App.squareRoot(0.0), DELTA);
        assertEquals(Double.NaN, App.squareRoot(-4.0));
    }

    @Test
    @DisplayName("Test Factorial Function")
    void testFactorial() {
        assertEquals(120, App.factorial(5));
        assertEquals(1, App.factorial(0));
        assertEquals(1, App.factorial(1));
        assertEquals(-1, App.factorial(-5)); // Error case
    }


    @Test
    @DisplayName("Test Natural Logarithm Function")
    void testNaturalLog() {
        assertEquals(0.0, App.naturalLog(1.0), DELTA);
        assertEquals(1.0, App.naturalLog(Math.E), DELTA);
        assertEquals(Double.NaN, App.naturalLog(-10));
    }

    @Test
    @DisplayName("Test Power Function")
    void testPower() {
        assertEquals(8.0, App.power(2.0, 3.0), DELTA);
        assertEquals(1.0, App.power(10.0, 0.0), DELTA);
        assertEquals(0.25, App.power(2.0, -2.0), DELTA);
    }
}




