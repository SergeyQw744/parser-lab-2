package org.example.calculator;

import org.example.parser.ArithmeticParser;

import java.util.*;

/**
 * Класс Calculator
 * Класс предназначен для вычисления значения арифметического выражения.
 * Основной метод, являющийся публичным:
 * @see #calculate(String expression)
 */
public class Calculator {
    /**
     * Метод calculate(String expression)
     * Метод принимает на вход строку, которая является арифметическим выражением.
     * Метод возвращает вещественное число - значение подаваемого на вход выражения.
     * Сначала выражение разбивается на токены с помощью метода tokenization() класса
     * ArithmeticParser. Далее мы приводим список токенов в постфиксную форму при помощи
     * метода toPostfix(List<Object> tokens). После чего возвращается значение, посчитанное
     * с помощью метода evaluatePostfix(Queue<Object> tokens).
     * @param expression арифметическое выражение
     * @see ArithmeticParser
     * @see #toPostfix(List tokens)
     * @see #evaluatePostfix(Queue tokens)
     */
    public double calculate(String expression) {
        List<Object> tokens = new ArithmeticParser(expression).tokenization();
        Queue<Object> postfixTokens = toPostfix(tokens);
        return evaluatePostfix(postfixTokens);
    }

    /**
     * Метод toPostfix(List<Object> tokens)
     * Метод принимает на вход список токенов, из которых состоит арифметическое выражение.
     * Метод возвращает очередь токенов, записанных в префиксной форме (знак операции расположен
     * после чисел, нет скобок). Создаются две коллекции - outputQueue и operatorStack.
     *  outputQueue - очередь, которая будет содержать результат в постфиксной записи и
     *  operatorStack - стек для хранения операторов и скобок. В цикле происходит обработка токенов.
     *  Если токен - число, то он добавляется в outputQueue, в противном случае токен является скобкой или
     *  оператором. Открывающая скобка добавляется в стек (operatorStack). Обработка закрывающихся скобок:
     *  извлекаются операторы из стека и добавляются в outputQueue, пока не встретится открывающая скобка.
     *  После этого открывающая скобка удаляется из стека. Обработка оператора: пока стек не пуст и приоритет
     *  текущего оператора меньше или равен приоритету верхнего оператора в стеке, операторы извлекаются из стека
     *  и добавляются в outputQueue, затем текущий оператор помещается в стек. Все оставшиеся операторы в
     *  operatorStack извлекаются и добавляются в outputQueue. В конце функция возвращает outputQueue, который
     *  теперь содержит элементы выражения в постфиксной форме.
     * @param tokens список токенов
     */
    private Queue<Object> toPostfix(List<Object> tokens) {
        Queue<Object> outputQueue = new LinkedList<>();
        Deque<Object> operatorStack = new ArrayDeque<>();

        for (Object token : tokens) {
            if (token instanceof Double) {
                outputQueue.add(token);
            } else if (token instanceof String) {
                String operator = (String) token;
                if (operator.equals("(")) {
                    operatorStack.push(operator);
                } else if (operator.equals(")")) {
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.pop(); // Удаляем '('
                } else {
                    while (!operatorStack.isEmpty() && precedence(operator) <= precedence((String) operatorStack.peek())) {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.push(operator);
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }

        return outputQueue;
    }

    /**
     * Метод precedence(String operator).
     * Метод принимает на вход односимвольную строку, которая принимает одно из следующих
     * значений: +, -, *, /. То есть на вход поступает арифметическая операция.
     * Метод возвращает целое число, которое обозначает приоритет арифметической операции.
     * Чем больше возвращаемое число, тем выше приоритет у арифметической операции.
     * @param operator строковое представление математической операции
     */
    private int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }

    /**
     * Метод evaluatePostfix(Queue<Object> tokens)
     * Метод принимает на вход очередь токенов, в которой токены находятся в постфиксной форме.
     * Метод возвращает посчитанное значение типа double (вычисляет значение выражения).
     * В цикле перебираются все токены, если попадается число, то оно добавляется в стек. Если
     * же попадается строка (оператор), то извлекаются два верхних числа из стека, после чего в зависимости от
     * знака операции, производится соответствующее вычисление, результат помещается в стек. К концу цикла
     * в стеке остается одно число - результат. Если среди токенов есть неизвестный (не являющийся +, -, *, /),
     * выбрасывается исключение IllegalArgumentException. Также, если происходит деление на нуль,
     * также выбрасывается исключение ArithmeticException.
     * @param tokens множество токенов, записанных в префиксной форме (очередь)
     * @throws ArithmeticException
     * @throws IllegalArgumentException
     */
    private double evaluatePostfix(Queue<Object> tokens) {
        Deque<Double> stack = new ArrayDeque<>();

        for (Object token : tokens) {
            if (token instanceof Double) {
                stack.push((Double) token);
            } else if (token instanceof String) {
                String operator = (String) token;
                double b = stack.pop();
                double a = stack.pop();
                double result;

                switch (operator) {
                    case "+":
                        result = a + b;
                        break;
                    case "-":
                        result = a - b;
                        break;
                    case "*":
                        result = a * b;
                        break;
                    case "/":
                        if (b == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        result = a / b;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + operator);
                }

                stack.push(result);
            }
        }

        return stack.pop();
    }
}