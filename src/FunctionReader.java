import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionReader {
    private String function;
    private double x;

    public FunctionReader(String function, double x) {
        if (function.contains("X")) {
            throw new IllegalArgumentException("Error: Variable 'X' must be lowercase 'x'. Please re-enter the function.");
        }
        this.function = function;
        this.x = x;
    }

    public double functionResult() {
        System.out.println("-- Enters functionResult");
        String substitutedFunction = this.function.replaceAll("x", "(" + String.valueOf(this.x) + ")");

        try {
            // Handle Euler's number 'e'
            substitutedFunction = substitutedFunction.replaceAll("e", String.valueOf(Math.E));

            // Handle square root
            substitutedFunction = substitutedFunction.replaceAll("sqrt\\((.*?)\\)", "Math.sqrt($1)");

            // Handle transcendental functions (sin, cos, tan)
            substitutedFunction = substitutedFunction.replaceAll("sin\\((.*?)\\)", "Math.sin($1)");
            substitutedFunction = substitutedFunction.replaceAll("cos\\((.*?)\\)", "Math.cos($1)");
            substitutedFunction = substitutedFunction.replaceAll("tan\\((.*?)\\)", "Math.tan($1)");
            System.out.println("substitutedFunction: "+substitutedFunction);

            // Handle exponentiation (^) by replacing with Math.pow
            Pattern powerPattern = Pattern.compile("(\\d+(\\.\\d+)?|\\(.*?\\))\\^(\\d+(\\.\\d+)?|\\(.*?\\))");
            Matcher powerMatcher = powerPattern.matcher(substitutedFunction);
            StringBuffer sb = new StringBuffer();
            while (powerMatcher.find()) {
                System.out.println("-- Enters while(powerMatcher.find())");
                String base = powerMatcher.group(1);
                String exponent = powerMatcher.group(3);
                String replacement = "(Math.pow(" + base + ", " + exponent + "))";
                powerMatcher.appendReplacement(sb, replacement);
                System.out.println("base: "+base+", exponent: "+exponent+", replacement: "+replacement);
            }
            System.out.println("Exits while(powerMatcher.find()) --");
            powerMatcher.appendTail(sb);
            substitutedFunction = sb.toString();
            System.out.println("substitutedFunction: "+substitutedFunction);

            // Basic arithmetic evaluation (very limited)
            System.out.println("Goes to evaluateSimpleExpression --");
            return evaluateSimpleExpression(substitutedFunction);

        } catch (Exception e) {
            System.err.println("Error evaluating the function: " + e.getMessage());
            return Double.NaN;
        }
    }

    private double evaluateSimpleExpression(String expression) {
        System.out.println("-- Enters evaluateSimpleExpression (expression: "+expression+" )");
        expression = expression.replaceAll("\\s+", "");
//        System.out.println("expression: "+expression);

        // Evaluate Math.pow()
        Pattern powerPatternEval = Pattern.compile("Math\\.pow\\(([-?\\d+(\\.\\d+)?]*),([-?\\d+(\\.\\d+)?]*)\\)");
        Matcher powerMatcherEval = powerPatternEval.matcher(expression);
        StringBuffer sbEval = new StringBuffer();
        while (powerMatcherEval.find()) {
            System.out.println("-- Enters while(powerMatcherEval.find())");
            double base = Double.parseDouble(powerMatcherEval.group(1).replaceAll("[()]", ""));
            double exponent = Double.parseDouble(powerMatcherEval.group(2).replaceAll("[()]", ""));
            powerMatcherEval.appendReplacement(sbEval, String.valueOf(Math.pow(base, exponent))+")");
        }
        System.out.println("Exits while(powerMatcherEval.find()) --");
        powerMatcherEval.appendTail(sbEval);
        expression = sbEval.toString();

//        // Evaluate Math.sqrt() //was moved below arithmetic operations
//        Pattern sqrtPattern = Pattern.compile("Math\\.sqrt\\(([-?\\d+(\\.\\d+)?]*)\\)");
//        Matcher sqrtMatcher = sqrtPattern.matcher(expression);
//        StringBuffer sbSqrt = new StringBuffer();
//        while (sqrtMatcher.find()) {
//            double argument = Double.parseDouble(sqrtMatcher.group(1).replaceAll("[()]", ""));
//            sqrtMatcher.appendReplacement(sbSqrt, String.valueOf(Math.sqrt(argument)));
//        }
//        sqrtMatcher.appendTail(sbSqrt);
//        expression = sbSqrt.toString();

//        // Evaluate sin, cos, tan
//        expression = evaluateTrigonometricFunctions(expression); //was moved below arithmetic operations

        // Basic multiplication and division
        while (expression.contains("*") || expression.contains("/")) {
            Pattern opPattern = Pattern.compile("(-?\\(?\\d+(\\.\\d+)?\\)?)([\\*/])(-?\\(?\\d+(\\.\\d+)?\\)?)");
            Matcher opMatcher = opPattern.matcher(expression);
            if (opMatcher.find()) {
                double operand1 = Double.parseDouble(opMatcher.group(1).replaceAll("[()]", ""));
                String operator = opMatcher.group(3);
                double operand2 = Double.parseDouble(opMatcher.group(4).replaceAll("[()]", ""));
                double result = 0;
                if (operator.equals("*")) {
                    result = operand1 * operand2;
                } else if (operator.equals("/")) {
                    result = operand1 / operand2;
                }
                expression = expression.replaceFirst(Pattern.quote(opMatcher.group(0)), "("+String.valueOf(result))+")";
            } else {
                break;
            }
        }

        // Basic addition and subtraction
        while (expression.contains("+")
                || (expression.startsWith("-") && expression.substring(1).contains("-")) || expression.substring(1).contains("-")
        ) {
            System.out.println("-- Enters addition and subtraction");
            Pattern opPattern = Pattern.compile("(-?\\(?\\d+(\\.\\d+)?\\)?)([+-])(-?\\(?\\d+(\\.\\d+)?\\)?)");
            Matcher opMatcher = opPattern.matcher(expression);
            if (opMatcher.find()) {
                System.out.println("-- Enters if(opMatcher.find()) --");
                double operand1 = Double.parseDouble(opMatcher.group(1).replaceAll("[()]", ""));
                String operator = opMatcher.group(3);
                double operand2 = Double.parseDouble(opMatcher.group(4).replaceAll("[()]", ""));
                double result = 0;
                if (operator.equals("+")) {
                    result = operand1 + operand2;
                } else if (operator.equals("-")) {
                    result = operand1 - operand2;
                }
                expression = expression.replaceFirst(Pattern.quote(opMatcher.group(0)), "("+String.valueOf(result))+")";
            } else {
                break;
            }
        }
        System.out.println("Exits addition and subtraction --");

        // Evaluate Math.sqrt()
        Pattern sqrtPattern = Pattern.compile("Math\\.sqrt\\(([-?\\d+(\\.\\d+)?]*)\\)");
        Matcher sqrtMatcher = sqrtPattern.matcher(expression);
        StringBuffer sbSqrt = new StringBuffer();
        while (sqrtMatcher.find()) {
            double argument = Double.parseDouble(sqrtMatcher.group(1).replaceAll("[()]", ""));
            sqrtMatcher.appendReplacement(sbSqrt, String.valueOf(Math.sqrt(argument)));
        }
        sqrtMatcher.appendTail(sbSqrt);
        expression = sbSqrt.toString();

//        // Evaluate sin, cos, tan
        expression = evaluateTrigonometricFunctions(expression);

        try {
            System.out.println("Exits evaluateSimpleExpression (try) --");
            return Double.parseDouble(expression);
        } catch (NumberFormatException e) {
            System.out.println("Exits evaluateSimpleExpression (catch) --");
            throw new RuntimeException("Could not evaluate the final expression: " + expression+" -- (evaluateSimpleExpression)");
        }
    }

    private String evaluateTrigonometricFunctions(String expression) {

        System.out.println("-- Enters evaluateTrigonometricFunctions (expression: "+expression+" )");
        // Evaluate sin
        Pattern sinPattern = Pattern.compile("Math\\.sin\\(([-?\\d+(\\.\\d+)?]*)\\)");
        Matcher sinMatcher = sinPattern.matcher(expression);
        StringBuffer sbSin = new StringBuffer();
        while (sinMatcher.find()) {
            System.out.println("-- Entered while(sinMatcher.find()) --");


            double argument = Double.parseDouble(sinMatcher.group(1).replaceAll("[()]", ""));
            sinMatcher.appendReplacement(sbSin, String.valueOf(Math.sin(argument)));






        }
        System.out.println("-- After while(sinMatcher.find()) --");
        sinMatcher.appendTail(sbSin);
        expression = sbSin.toString();

        // Evaluate cos
        Pattern cosPattern = Pattern.compile("Math\\.cos\\(([-?\\d+(\\.\\d+)?]*)\\)");
        Matcher cosMatcher = cosPattern.matcher(expression);
        StringBuffer sbCos = new StringBuffer();
        while (cosMatcher.find()) {
            double argument = Double.parseDouble(cosMatcher.group(1).replaceAll("[()]", ""));
            cosMatcher.appendReplacement(sbCos, String.valueOf(Math.cos(argument)));
        }
        cosMatcher.appendTail(sbCos);
        expression = sbCos.toString();

        // Evaluate tan
        Pattern tanPattern = Pattern.compile("Math\\.tan\\(([-?\\d+(\\.\\d+)?]*)\\)");
        Matcher tanMatcher = tanPattern.matcher(expression);
        StringBuffer sbTan = new StringBuffer();
        while (tanMatcher.find()) {
            double argument = Double.parseDouble(tanMatcher.group(1).replaceAll("[()]", ""));
            tanMatcher.appendReplacement(sbTan, String.valueOf(Math.tan(argument)));
        }
        tanMatcher.appendTail(sbTan);
        expression = sbTan.toString();

        System.out.println("Exits evaluateTrigonometricFunctions --");
        return expression;
    }

    @Override
    public String toString() {
        return String.format("f(x) = %s : value of x = %f", this.function, this.x);
    }

    //testing the function
    public static void main(String[] args) {
        System.out.println("-- Enters main");
        FunctionReader reader1 = new FunctionReader("sqrt(x+2)", 2); //still has an error when solving extra complex equations and square roots with addition inside:/
        System.out.println(reader1);
        System.out.println("Result: " + reader1.functionResult());
        System.out.println("Exits main --");
    }
    //error equations
    //e^sin(x^2)
    //e^sin(x^2+3)
    //e^sqrt(2+x)
    //sin(x^2+3)
    //sin(3x) = -0.2794

}
