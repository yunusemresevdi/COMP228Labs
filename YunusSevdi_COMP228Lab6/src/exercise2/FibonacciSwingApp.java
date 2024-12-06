package exercise2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FibonacciSwingApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Input field for the number of Fibonacci terms
        TextField inputField = new TextField();
        inputField.setPromptText("Enter the number of Fibonacci terms");

        // Generate button
        Button generateButton = new Button("Generate");

        // Result area to display Fibonacci numbers
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        // Set up the action for the Generate button
        generateButton.setOnAction(event -> {
            System.out.println("Generate button clicked!"); // Debug message

            String input = inputField.getText();
            try {
                if (input.trim().isEmpty()) {
                    throw new NumberFormatException("Input cannot be empty.");
                }

                int n = Integer.parseInt(input);
                if (n <= 0) {
                    throw new NumberFormatException("Number must be greater than 0.");
                }

                // Clear previous results
                resultArea.clear();

                // Create and start the FibonacciTask
                FibonacciTask task = new FibonacciTask(n);

                // Update the UI when the task succeeds
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    resultArea.appendText("Fibonacci series in reverse order:\n");
                    task.getValue().forEach(number -> {
                        Platform.runLater(() -> resultArea.appendText(number + "\n"));
                    });
                });

                // Handle task failure
                task.setOnFailed(e -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Task failed.");
                    alert.showAndWait();
                });

                // Run the task in a separate thread
                new Thread(task).start();
            } catch (NumberFormatException ex) {
                // Show an error alert if the input is invalid
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid positive number.");
                alert.showAndWait();
            }
        });

        // Layout setup
        VBox layout = new VBox(10, inputField, generateButton, resultArea);
        layout.setSpacing(10);

        // Scene and Stage setup
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fibonacci Generator");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
