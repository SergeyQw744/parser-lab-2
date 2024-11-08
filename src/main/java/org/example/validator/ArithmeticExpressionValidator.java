package org.example;

import org.example.templates.Numbers;
import org.example.templates.Operations;
import org.example.templates.Staples;

import java.util.ArrayList;
import java.util.List;

public class ArithmeticExpressionValidator {

    public ArithmeticExpressionValidator(){}
    public boolean validatedStaples(String arithmeticExpression){
        String[] chars = arithmeticExpression.split("");
        List<String[]> pairsStaples = new ArrayList<>();
        String[] samplePair = {"(", ")"};
        int i = 0; int j = 0;

        for (String elem : chars){
            if (elem.equals("(")){
                String[] staples = new String[2];
                staples[0] = "("; staples[1] = "";
                pairsStaples.add(staples);
                i++;
            }
            if (elem.equals(")")){
                if (i <= j){
                    String[] staples = new String[2];
                    staples[1] = ")"; staples[0] = "";
                    pairsStaples.add(staples);
                }
                pairsStaples.get(j)[1] = ")";
                j++;
            }
        }

        for (int k = 0; k < pairsStaples.size(); k++){
            if (!eq(pairsStaples.get(k), samplePair)){
                return false;
            }
        }
        for (int k = 0; k < chars.length - 1; k++){
            if (chars[k].equals("(") && chars[k+1].equals(")")){
                return false;
            }
        }
        for (int k = 0; k < chars.length - 1; k++){
            if (chars[k].equals(")") && chars[k+1].equals("(")){
                return false;
            }
        }
        return true;
    }

    public boolean validatedDots(String arithmeticExpression){
        String[] chars = arithmeticExpression.split("");
        List<String> listOfNumbers = new Numbers().getTemplates();
        if (chars[0].equals(".") || chars[chars.length - 1].equals(".")){
            return false;
        }
        for (int i = 1; i < chars.length - 1; i++){
            if (chars[i].equals(".")){
                boolean validationCondition = listOfNumbers.contains(chars[i-1]) && listOfNumbers.contains(chars[i+1]);
                if (!validationCondition){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validatedOperands(String arithmeticExpression){
        String[] chars = arithmeticExpression.split("");
        List<String> listOfOperations = new Operations().getTemplates();
        List<String> listOfStaples = new Staples().getTemplates();
        if (listOfOperations.contains(chars[0]) || listOfOperations.contains(chars[chars.length - 1])){
            return false;
        }
        for (int i = 1; i < chars.length - 1; i++){
            if (listOfOperations.contains(chars[i])){
                boolean validationCondition = (listOfOperations.contains(chars[i-1]) || listOfOperations.contains(chars[i+1])) ||
                        listOfStaples.get(0).equals(chars[i-1]) ||
                        listOfStaples.get(1).equals(chars[i+1]);
                if (validationCondition){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean eq(String[] actual, String[] samplePair){
        for (int i = 0; i < actual.length; i++){
            if (!actual[i].equals(samplePair[i])){
                return false;
            }
        }
        return true;
    }

    public boolean validatedNumberManyDots(String number){
        String[] chars = number.split("");
        int counterDots = 0;
        for(String elem : chars){
            if (elem.equals(".")){
                counterDots++;
            }
        }
        if (counterDots < 2){
            return true;
        } else {
            return false;
        }
    }


}
