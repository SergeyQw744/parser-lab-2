private List<Object> infixToPostfix(List<Object> tokens) {
        List<Object> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        for (Object token : tokens) {
            if (token instanceof Number) {
                output.add(token);
            } else if (token instanceof String) {
                String operator = (String) token;
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(operator)) {
                    output.add(operators.pop());
                }
                operators.push(operator);
            }
            // Здесь нужно добавить обработку скобок
        }

        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
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



        private double evaluatePostfix(List<Object> tokens) {
                Stack<Double> stack = new Stack<>();

                for (Object token : tokens) {
                    if (token instanceof Number) {
                        stack.push((Double) token);
                    } else if (token instanceof String) {
                        String operator = (String) token;
                        double b = stack.pop();
                        double a = stack.pop();
                        double result = applyOperator(operator, a, b);
                        stack.push(result);
                    }
                }

                return stack.pop();
            }





            private List<Object> infixToPostfix(List<Object> tokens) {
                    List<Object> output = new ArrayList<>();
                    Stack<String> operators = new Stack<>();

                    for (Object token : tokens) {
                        if (token instanceof Number) {
                            output.add(token);
                        } else if (token instanceof String) {
                            String operator = (String) token;
                            if (operator.equals("(")) {
                                operators.push(operator);
                            } else if (operator.equals(")")) {
                                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                                    output.add(operators.pop());
                                }
                                operators.pop();
                            } else {
                                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(operator)) {
                                    output.add(operators.pop());
                                }
                                operators.push(operator);
                            }
                        }
                    }
                    while (!operators.isEmpty()) {
                        output.add(operators.pop());
                    }

                    return output;
                }













