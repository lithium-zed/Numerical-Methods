import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
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

    private String evaluateExpression(String expression) {
        try {
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
        String expressionToEvaluate = function.replace(variableName, String.valueOf(value));
        return evaluateExpression(expressionToEvaluate);
    }

    public static void main(String[] args) {
        MathJSAPIConnection connection = new MathJSAPIConnection();

        // Example 1: Evaluate a function at a value
        String function1 = "sin(x^3 + 2) + e^x";
        String variable1 = "x";
        double value1 = 4.0;
        String result1 = connection.evaluateFunctionAtValue(function1, value1);
        System.out.println("Result of " + function1 + " at " + variable1 + "=" + value1 + ": " + result1);

    }
}
