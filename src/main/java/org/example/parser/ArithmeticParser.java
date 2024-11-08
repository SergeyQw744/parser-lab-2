package org.example;

import org.example.exceptions.InvalidArithmeticExpressionException;
import org.example.templates.Numbers;

import java.util.ArrayList;
import java.util.List;

public class ArithmeticParser {

    private String validation(String expression){
        ArithmeticExpressionValidator validator = new ArithmeticExpressionValidator();
        StringBuilder errorsMessage = new StringBuilder();
        if (!validator.validatedStaples(expression)){
            errorsMessage.append("The staples are incorrectly placed");
        }
        if (!validator.validatedOperands(expression)){
            errorsMessage.append("The operands are incorrectly placed");
        }
        if (!validator.validatedDots(expression) || !validator.validatedNumberManyDots(expression)){
            errorsMessage.append("The dots are incorrectly placed");
        }
        return errorsMessage.toString();
    }

    public List<Object> tokenization(String expression){
        if (!validation(expression).isBlank()){
            throw new InvalidArithmeticExpressionException(validation(expression));
        }
        String[] chars = expression.split("");
        List<String> listOfNumbers = new Numbers().getTemplates();
        List<Object> numbersAndOperands = new ArrayList<>();
        for (int i = 0; i < chars.length; i++){
            if (i >= 1 && chars[i].equals("-")){
                String numberNegativeStr = chars[i];
                int startNegativeNumber = i;
                if (chars[i-1].equals("(")){
                    while((listOfNumbers.contains(chars[startNegativeNumber]) || chars[startNegativeNumber].equals(".") || chars[startNegativeNumber].equals("-"))
                            && startNegativeNumber < chars.length - 1){
                        startNegativeNumber++;
                        if (listOfNumbers.contains(chars[startNegativeNumber]) || chars[startNegativeNumber].equals(".") || chars[startNegativeNumber].equals("-")){
                            numberNegativeStr = numberNegativeStr + chars[startNegativeNumber];
                            i++;
                        }
                    }
                    if (numberNegativeStr.contains(".")){
                        numbersAndOperands.add(Double.parseDouble(numberNegativeStr));
                    } else {
                        numbersAndOperands.add(Integer.parseInt(numberNegativeStr));
                    }
                }
            }
            if (listOfNumbers.contains(chars[i])){
                String numberStr = chars[i];
                int startNumber = i;
                while((listOfNumbers.contains(chars[startNumber]) || chars[startNumber].equals(".")) && startNumber < chars.length - 1){
                    startNumber++;
                    if (listOfNumbers.contains(chars[startNumber]) || chars[startNumber].equals(".")){
                        numberStr = numberStr + chars[startNumber];
                        i++;
                    }
                }
                if (numberStr.contains(".")){
                    numbersAndOperands.add(Double.parseDouble(numberStr));
                } else {
                    numbersAndOperands.add(Integer.parseInt(numberStr));
                }
            } else {
                numbersAndOperands.add(chars[i]);
            }
        }
        adjustingTokens(numbersAndOperands);
        return numbersAndOperands;
    }

    private void adjustingTokens(List<Object> tokens){
        for (int i = 0; i < tokens.size(); i++){
            Object elem = tokens.get(i);
            if (elem instanceof Integer p ){
                if (p < 0){
                    i++;
                }
            }
            if (elem instanceof Double p){
                if (p < 0){
                    i++;
                }
            }
        }
    }

}
