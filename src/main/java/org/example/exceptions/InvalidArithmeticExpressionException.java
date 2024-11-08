package org.example.exceptions;


/**
 * Класс InvalidArithmeticExpressionException
 * Этот класс является исключением, которое содержит описание ошибки.
 * Может быть выброшено при валидации выражения при работе с классом
 * ArithmeticParser
 */
public class InvalidArithmeticExpressionException extends RuntimeException{
    public InvalidArithmeticExpressionException(String msg){
        super(msg);
    }
}
