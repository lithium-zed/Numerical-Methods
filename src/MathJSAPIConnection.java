import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MathJSAPIConnection {

    private final String API_URL = "http://api.mathjs.org/v4/";
    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private final String variableName;

    public MathJSAPIConnection() {
        this.variableName = "x";
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    public String formatFunction(String function) {
        // Handle cases like 3x, -2x, x^2, etc.
        Pattern pattern = Pattern.compile("([-]?\\d*)" + Pattern.quote(variableName) + "(\\^(\\d+))?");
        Matcher matcher = pattern.matcher(function);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String coefficient = matcher.group(1);
            String powerPart = matcher.group(3);

            String replacement;
            if (coefficient.isEmpty() || coefficient.equals("+") || coefficient.equals("-")) {
                replacement = (coefficient.isEmpty() ? "1" : (coefficient.equals("-") ? "-1" : "1")) + "(" + variableName + ")" + (powerPart != null ? "^" + powerPart : "");
            } else {
                replacement = coefficient + "(" + variableName + ")" + (powerPart != null ? "^" + powerPart : "");
            }
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    private String evaluateExpression(String expression) {
        try {
            if (expression.contains("X")) {
                throw new IllegalArgumentException("Error: Variable 'X' must be lowercase 'x'. Please re-enter the function.");
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"expr\": \"" + expression + "\"}", StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                if (root.has("result")) {
                    return root.get("result").asText();
                } else if (root.has("error")) {
                    return "Error: " + root.get("error").asText(); // Include "Error: " for consistent error prefix
                } else {
                    return "Error: Unexpected API response: " + response.body(); // Include response body in error
                }
            } else {
                return "Error: API request failed with status code " + response.statusCode() +
                        ", response body: " + response.body(); // Include status code and response
            }
        } catch (Exception e) {
            return "Error: An exception occurred during the API call: " + e.getMessage(); // More specific error
        }
    }

    public String evaluateFunctionAtValue(String function, double value) {
        String formattedFunction = formatFunction(function);
        String expressionToEvaluate = formattedFunction.replace(variableName, String.valueOf(value));
        return evaluateExpression(expressionToEvaluate);
    }

    //for testing function evaluation
//    public static void main(String[] args) {
//        MathJSAPIConnection connection = new MathJSAPIConnection();
//
//
//        String function1 = "e^sin(x^2 + 3)";
//        double value1 = 4.0;
//        String result1 = connection.evaluateFunctionAtValue(function1, value1);
//        System.out.println(connection.formatFunction(function1));
//        System.out.println(result1);
//
//    }
}
