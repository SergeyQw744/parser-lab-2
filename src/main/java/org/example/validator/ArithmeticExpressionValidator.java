package org.example.validator;

import org.example.templates.Numbers;
import org.example.templates.Operations;
import org.example.templates.Staples;

import java.util.ArrayList;
import java.util.List;


/**
 * Класс ArithmeticExpressionValidator
 * Класс предназначен для валидации арифметических выражений записанных в виде строки.
 * Есть следующие публичные методы:
 * @see #validatedStaples(String arithmeticExpression)
 * @see #validatedDots(String arithmeticExpression)
 * @see #validatedOperands(String arithmeticExpression)
 * @see #validatedNumberManyDots(String number)
 */
public class ArithmeticExpressionValidator {

    public ArithmeticExpressionValidator(){}

    /**
     * Метод validatedStaples(String arithmeticExpression).
     * На вход метод принимает строку, содержащую математическое выражение.
     * Метод возвращает true или false. Первое значение указывает на корректность выражение, второе - наоборот.
     * В начале создается список строковых массивов, который будет использоваться для хранения пар скобок
     * Для получения скобок, как отдельных элементов применяется метод split у класса String.
     * Также инициализируются 2 счетчик для скобок (отдельно открывающихся и закрывающихся).
     * При обнаружении очередной скобки в массиве символов она попадает в свободную заготовку для пары в списке,
     * если она является открывающейся, закрывающиеся скобки могут только добавляться в пары к открывающимся.
     * После происходит проверка пар скобок (у каждой открывающейся должна быть закрывающаяся),
     * метод boolean eq(String[] actual, String[] samplePair).
     * Также происходит проверка на наличие в выражении неправильных конструкций: () и )(.
     * @param arithmeticExpression математическое выражение
     * @see #eq(String[] actual, String[] samplePair)
     */
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

    /**
     * Метод validatedDots(String arithmeticExpression).
     * На вход метод принимает строку, содержащую математическое выражение.
     * Метод возвращает true или false. Первое значение указывает на корректность выражение, второе - наоборот.
     * Сначала происходит разбиение выражения на отдельные символы при помощи метода split у класса String.
     * Также понадобится список цифр, для того, чтобы проверять, что точка находится внутри числа.
     * Сначала сделаем проверку на тот факт, что выражение не может начинаться и заканчиваться точкой.
     * Дальше происходит проверка на то, чтобы убедиться, что все точки используются в вещественных числах.
     * Это значит, что справа и слева от точки должны находиться цифры, иначе выражение не валидно.
     * @param arithmeticExpression математическое выражение
     */
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

    /**
     * Метод validatedOperands(String arithmeticExpression).
     * На вход метод принимает строку, содержащую математическое выражение.
     * Метод возвращает true или false. Первое значение указывает на корректность выражение, второе - наоборот.
     * Сначала происходит разбиение выражения на отдельные символы при помощи метода split у класса String.
     * Также понадобятся списки скобок и операций для проверки выражения на корректность.
     * Проводится проверка на то, что выражение не начинается со знака операций.
     * Также потом происходит проверка того, чтобы не было нескольких знаков операций подряд, а также то, как могут
     * располагаться знаки операций и скобки относительно друг друга. Также учитывается и то, что выражение может
     * содержать отрицательные числа.
     * @param arithmeticExpression математическое выражение
     */
    public boolean validatedOperands(String arithmeticExpression){
        String[] chars = arithmeticExpression.split("");
        List<String> listOfOperations = new Operations().getTemplates();
        List<String> listOfStaples = new Staples().getTemplates();
        if (listOfOperations.contains(chars[0]) || listOfOperations.contains(chars[chars.length - 1])){
            return false;
        }
        for (int i = 1; i < chars.length - 1; i++){
            if (listOfOperations.contains(chars[i]) && !chars[i].equals("-")){
                boolean validationCondition = (listOfOperations.contains(chars[i-1]) || listOfOperations.contains(chars[i+1])) ||
                        listOfStaples.get(0).equals(chars[i-1]) ||
                        listOfStaples.get(1).equals(chars[i+1]);
                if (validationCondition){
                    return false;
                }
            }
            if (chars[i].equals("-")){
                boolean validationCondition = (listOfOperations.contains(chars[i-1]) || listOfOperations.contains(chars[i+1])) ||
                        listOfStaples.get(1).equals(chars[i+1]);
                if (validationCondition){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Метод eq(String[] actual, String[] samplePair).
     * На вход метод принимает два строковых массива: актуальный и образец
     * Метод возвращает true или false. Если поступающие на вход массивы не равны, возвращается false,
     * иначе true.
     * @param actual массив, который надо сравнить.
     * @param samplePair образец, с которым происходит сравнение.
     */
    private boolean eq(String[] actual, String[] samplePair){
        for (int i = 0; i < actual.length; i++){
            if (!actual[i].equals(samplePair[i])){
                return false;
            }
        }
        return true;
    }

    /**
     * Метод validatedNumberManyDots(String number).
     * На вход метод принимает строку, которая является числом.
     * Метод возвращает true или false. Возвращает true - если число прошло проверку, false - в противном случае.
     * Метод подсчитывает количество точек в числе, которых должно быть не более 1.
     * @param number строковое число.
     */
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