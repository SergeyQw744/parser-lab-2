package org.example.calculator;

import org.example.parser.ArithmeticParser;

import java.util.*;

public class Calculator3 {
    public double calculate(String expression) {
        List<Object> tokens = new ArithmeticParser().tokenization(expression);
        Queue<Object> postfixTokens = toPostfix(tokens);
        return evaluatePostfix(postfixTokens);
    }

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

