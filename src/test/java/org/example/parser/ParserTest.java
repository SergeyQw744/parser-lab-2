package org.example.parser;

import org.example.parser.ArithmeticParser;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void initParser(){
        parser = new ArithmeticParser("2*(-5+2)-4*(-1.3)");
    }

    @Test
    void tokenization_returnedListOfTokens(){
        List<Object> tokens = List.of(2.0, "*", "(", -5.0, "+", 2.0, ")", "-", 4.0, "*", "(", -1.3, ")");
        assertEquals(parser.tokenization(), tokens);
    }
}
