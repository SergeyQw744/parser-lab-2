package org.example;

import org.example.parser.ArithmeticParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ParserTest {
    @InjectMocks
    ArithmeticParser parser;

    @Test
    void tokenization_returnedListOfTokens(){
        String expression = "2*(-5+2)-4*(-1.3)";
        List<Object> tokens = List.of(2.0, "*", "(", -5.0, "+", 2.0, ")", "-", 4.0, "*", "(", -1.3, ")");
        assertEquals(parser.tokenization(), tokens);
    }
}
