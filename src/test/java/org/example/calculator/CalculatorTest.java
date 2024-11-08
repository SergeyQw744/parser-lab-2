package org.example.calculator;

import org.example.calculator.Calculator;
import org.example.exceptions.InvalidArithmeticExpressionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CalculatorTest {
    @InjectMocks
    Calculator calculator;

    @Test
    void testCalculateSummation_returnedValue(){
        assertEquals(calculator.calculate("3+7+1"), 11.0);
        assertEquals(calculator.calculate("4.5+1+4.4"), 9.9);
        assertEquals(calculator.calculate("2+(-2)"), 0.0);
    }

    @Test
    void testCalculateDifference_returnedValue(){
        assertEquals(calculator.calculate("5-2.3"), 2.7);
        assertEquals(calculator.calculate("50-50"), 0.0);
        assertEquals(calculator.calculate("5-3-(-1)"), 3.0);
    }

    @Test
    void testCalculateMultiplication_returnedValue(){
        assertEquals(calculator.calculate("2*4*5"), 40.0);
        assertEquals(calculator.calculate("2*(-4)*5"), -40.0);
        assertEquals(calculator.calculate("2*(-4.5)*(-5)"), 45.0);
    }

    @Test
    void testCalculateDivision_returnedValue(){
        assertEquals(calculator.calculate("5/(-2.5)"), -2);
        assertEquals(calculator.calculate("5/2"), 2.5);
        assertEquals(calculator.calculate("0/(-2)"), -0.0);
    }

    @Test
    void testCalculateExpressionWithStaples_returnedValue(){
        assertEquals(calculator.calculate("2*(2*(2+3))"), 20.0);
        assertEquals(calculator.calculate("2*(-5+1)-9*(-1)"), 1.0);
        assertEquals(calculator.calculate("(4+0.16)/(1+3)"), 1.04);
    }

    @Test
    void testCalculateInvalidException_throwException(){
        assertThrows(InvalidArithmeticExpressionException.class, () -> calculator.calculate("2.3.0+56"));
        assertThrows(InvalidArithmeticExpressionException.class, () -> calculator.calculate("5*(3+1))-9"));
        assertThrows(InvalidArithmeticExpressionException.class, () -> calculator.calculate("4-(-6)+*2"));
    }
}
