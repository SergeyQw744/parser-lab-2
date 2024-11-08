package org.example.parser;

import org.example.validator.ArithmeticExpressionValidator;
import org.example.exceptions.InvalidArithmeticExpressionException;
import org.example.templates.Numbers;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс ArithmeticParser
 * Класс предназначен для разбиения выражения на токены: скобки, знаки операций, числа
 * Содержит два поля: String expression - строковое математическое выражение и
 * ArithmeticExpressionValidator validator - объект для проверки корректности выражения
 * При создании объекта ArithmeticParser требуется задать выражение, а валидирующий объект
 * создается за пользователя. Основной метод:
 * @see #tokenization()
 */
public class ArithmeticParser {
    private String expression;
    private ArithmeticExpressionValidator validator;
    public ArithmeticParser(String expression) {
        this.expression = expression;
        this.validator = new ArithmeticExpressionValidator();
    }
    /**
     * Метод validation().
     * Метод не принимает аргументов.
     * Метод возвращает строковое значение, которое содержит описание ошибок или может быть пустым,
     * если ошибок нет. Создается объект класса StringBuilder для построения сообщения с ошибками.
     * Выражение проверяется на правильность расположения скобок, потом операций и точек, однако
     * без проверки количества точек в вещественном числе. Для этих целей используется класс
     * @see ArithmeticExpressionValidator
     */
    private String validation(){
        StringBuilder errorsMessage = new StringBuilder();
        if (!validator.validatedStaples(expression)){
            errorsMessage.append("The staples are incorrectly placed! ");
        }
        if (!validator.validatedOperands(expression)){
            errorsMessage.append("The operands are incorrectly placed! ");
        }
        if (!validator.validatedDots(expression)){
            errorsMessage.append("The dots are incorrectly placed! ");
        }
        return errorsMessage.toString();
    }

    /**
     * Метод tokenization().
     * Метод не принимает на вход ничего.
     * При этом метод возвращает список токенов, из которых состоит математическое выражение.
     * Токенами в данном случае называются скобки, числа и знаки математических операций.
     * Перед выделением токенов выражение проверяется на корректность, для чео используется метод
     * validation(), если выражение не валидно, то выбрасывается исключение InvalidArithmeticExpressionException.
     * Подаваемое на вход выражение при помощи метода split у класса String разделяется на символы.
     * Проходя по всем символам, ищутся символы, которые являются цифрами. Когда цифра найдена,
     * метод продолжает поиск других цифр и точек, находящихся сразу после найденной, они накапливаются в строке
     * numberStr. Потом последовательность цифр и точек найдена, она проверяется на корректность по
     * количеству точек (в числе может быть не более 1 точки), для чего используется поле validator. В завершении
     * при помощи класса Double происходит парсинг в вещественное число и это число добавляется в список токенов
     * (numbersAndOperands). Для отрицательных чисел процесс похож, однако такие числа начинаются со знака минус
     * и также перед минусом у таких чисел часто встречаются скобка (, например как в выражении: 2*(-2).
     * Токены, которые не являются числами, просто добавляются в общий список. После чего список токенов
     * проходит корректировку при помощи метода adjustingTokens(List<?> tokens), поскольку при обработке отрицательных
     * чисел данная реализация приводит к появлению побочных токенов.
     * @see #validation()
     * @see #adjustingTokens(List numbersAndOperands)
     * @throws InvalidArithmeticExpressionException
     */
    public List<Object> tokenization(){
        if (!validation().isBlank()){
            throw new InvalidArithmeticExpressionException(validation());
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
                    if (!validator.validatedNumberManyDots(numberNegativeStr)){
                        throw new InvalidArithmeticExpressionException("The dots are incorrectly placed! ");
                    }
                    numbersAndOperands.add(Double.parseDouble(numberNegativeStr));
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
                if (!validator.validatedNumberManyDots(numberStr)){
                    throw new InvalidArithmeticExpressionException("The dots are incorrectly placed! ");
                }
                numbersAndOperands.add(Double.parseDouble(numberStr));
            } else {
                numbersAndOperands.add(chars[i]);
            }
        }
        return adjustingTokens(numbersAndOperands);
    }

    /**
     * Метод adjustingTokens(List<?> tokens).
     * Метод принимает на вход список токенов, который следует корректировать.
     * Поскольку особенность работы метода tokenization() заключается в том, что
     * при обработке каждого отрицательного числа появляется лишний токен, который требуется
     * игнорировать, причем этот токен появляется в списке сразу после отрицательного числа.
     * Метод выводит скорректированный список токенов (без побочных).
     * @param tokens список, содержащий побочные токены.
     */
    private List<Object> adjustingTokens(List<Object> tokens){
        List<Object> correctTokens = new ArrayList<>();
        int j = 0;
        while (j < tokens.size()){
            Object elem = tokens.get(j);
            if (elem instanceof Double p && p < 0){
                correctTokens.add(elem);
                j++;
            } else {
                correctTokens.add(elem);
            }
            j++;
        }
        return correctTokens;
    }
}
