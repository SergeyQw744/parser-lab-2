package org.example;

import org.example.validator.ArithmeticExpressionValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ValidatorTest {
    @InjectMocks
    ArithmeticExpressionValidator validator;
    @Test
    void testValidatedStaples_returnedBooleanSignal(){
        String validExpression = "(3+8)*(1-(9+2))*(-2)";
        String invalidExpression1 = "2*(4-5))";
        String invalidExpression2 = "(4+9)*()";
        String invalidExpression3 = "4-(-9(";
        String invalidExpression4 = "(5+6)(-6+1)";
        assertEquals(validator.validatedStaples(validExpression), true);
        assertEquals(validator.validatedStaples(invalidExpression1), false);
        assertEquals(validator.validatedStaples(invalidExpression2), false);
        assertEquals(validator.validatedStaples(invalidExpression3), false);
        assertEquals(validator.validatedStaples(invalidExpression4), false);
    }

    @Test
    void testValidatedDots_returnedBooleanSignal(){
        String validExpression = "5.3+9-6.3*1.2";
        String invalidExpression1 = "4.+89";
        String invalidExpression2 = ".9+63";
        String invalidExpression3 = "4.5+.(7+6)";
        assertEquals(validator.validatedDots(validExpression), true);
        assertEquals(validator.validatedDots(invalidExpression1), false);
        assertEquals(validator.validatedDots(invalidExpression2), false);
        assertEquals(validator.validatedDots(invalidExpression3), false);
    }

    @Test
    void testValidatedNumberManyDots_returnedBooleanSignal(){
        String expression = "4.3.6+89-1.2";
        assertEquals(validator.validatedNumberManyDots(expression), false);
    }

    @Test
    void testValidatedOperands_returnedBooleanSignal(){
        String validExpression = "6+(8.3-5)*(-2)";
        assertEquals(validator.validatedOperands(validExpression), true);
    }
}
