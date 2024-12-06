package exercise1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class GameApp extends Application {
    private TableView<Player> playerTable;
    private TableView<Game> gameTable;
    private TableView<PlayerAndGame> playerAndGameTable;
    private TextField playerIdField, firstNameField, lastNameField, addressField, postalCodeField,
            provinceField, phoneNumberField, gameIdField, gameTitleField, playerGameIdField,
            playingDateField, scoreField;
    private ComboBox<Integer> playerIdFilterComboBox;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Player Management");

        // Input Fields for Player
        playerIdField = new TextField();
        playerIdField.setPromptText("Player ID");
        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        addressField = new TextField();
        addressField.setPromptText("Address");
        postalCodeField = new TextField();
        postalCodeField.setPromptText("Postal Code");
        provinceField = new TextField();
        provinceField.setPromptText("Province");
        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number");

        // Input Fields for Game
        gameIdField = new TextField();
        gameIdField.setPromptText("Game ID");
        gameTitleField = new TextField();
        gameTitleField.setPromptText("Game Title");

        // Input Fields for PlayerAndGame
        playerGameIdField = new TextField();
        playerGameIdField.setPromptText("Player Game ID");
        playingDateField = new TextField();
        playingDateField.setPromptText("Playing Date (YYYY-MM-DD)");
        scoreField = new TextField();
        scoreField.setPromptText("Score");

        // ComboBox for filtering by Player ID
        playerIdFilterComboBox = new ComboBox<>();
        playerIdFilterComboBox.setPromptText("Filter by Player ID");

        // Buttons
        Button insertPlayerButton = new Button("Insert Player");
        Button updatePlayerButton = new Button("Update Player");
        Button insertGameButton = new Button("Insert Game");
        Button insertPlayerAndGameButton = new Button("Insert Player and Game");
        Button displayPlayerButton = new Button("Display Players");
        Button displayGameButton = new Button("Display Games");
        Button displayPlayerAndGameButton = new Button("Display Player and Game");

        // Player Table
        playerTable = new TableView<>();
        playerTable.getColumns().addAll(
                createColumn("Player ID", "playerId", Integer.class),
                createColumn("First Name", "firstName", String.class),
                createColumn("Last Name", "lastName", String.class),
                createColumn("Address", "address", String.class),
                createColumn("Postal Code", "postalCode", String.class),
                createColumn("Province", "province", String.class),
                createColumn("Phone Number", "phoneNumber", String.class)
        );

        // Game Table
        gameTable = new TableView<>();
        gameTable.getColumns().addAll(
                createColumn("Game ID", "gameId", Integer.class),
                createColumn("Game Title", "gameTitle", String.class)
        );

        // PlayerAndGame Table
        playerAndGameTable = new TableView<>();
        playerAndGameTable.getColumns().addAll(
                createColumn("Player Game ID", "playerGameId", Integer.class),
                createColumn("Player ID", "playerId", Integer.class),
                createColumn("Game ID", "gameId", Integer.class),
                createColumn("Playing Date", "playingDate", String.class),
                createColumn("Score", "score", Integer.class)
        );

        // Layout
        GridPane inputGrid = new GridPane();
        inputGrid.setPadding(new Insets(10));
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        inputGrid.add(new Label("Player ID:"), 0, 0);
        inputGrid.add(playerIdField, 1, 0);
        inputGrid.add(new Label("First Name:"), 0, 1);
        inputGrid.add(firstNameField, 1, 1);
        inputGrid.add(new Label("Last Name:"), 0, 2);
        inputGrid.add(lastNameField, 1, 2);
        inputGrid.add(new Label("Address:"), 0, 3);
        inputGrid.add(addressField, 1, 3);
        inputGrid.add(new Label("Postal Code:"), 0, 4);
        inputGrid.add(postalCodeField, 1, 4);
        inputGrid.add(new Label("Province:"), 0, 5);
        inputGrid.add(provinceField, 1, 5);
        inputGrid.add(new Label("Phone Number:"), 0, 6);
        inputGrid.add(phoneNumberField, 1, 6);
        inputGrid.add(insertPlayerButton, 0, 7);
        inputGrid.add(updatePlayerButton, 1, 7);

        inputGrid.add(new Label("Game ID:"), 2, 0);
        inputGrid.add(gameIdField, 3, 0);
        inputGrid.add(new Label("Game Title:"), 2, 1);
        inputGrid.add(gameTitleField, 3, 1);
        inputGrid.add(insertGameButton, 2, 2);

        inputGrid.add(new Label("Player Game ID:"), 4, 0);
        inputGrid.add(playerGameIdField, 5, 0);
        inputGrid.add(new Label("Playing Date:"), 4, 1);
        inputGrid.add(playingDateField, 5, 1);
        inputGrid.add(new Label("Score:"), 4, 2);
        inputGrid.add(scoreField, 5, 2);
        inputGrid.add(insertPlayerAndGameButton, 4, 3);

        inputGrid.add(new Label("Filter by Player ID:"), 0, 8);
        inputGrid.add(playerIdFilterComboBox, 1, 8);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(inputGrid, displayPlayerButton, playerTable, displayGameButton, gameTable, displayPlayerAndGameButton, playerAndGameTable);

        // Event Listeners
        insertPlayerButton.setOnAction(e -> insertPlayer());
        updatePlayerButton.setOnAction(e -> updatePlayer());
        insertGameButton.setOnAction(e -> insertGame());
        insertPlayerAndGameButton.setOnAction(e -> insertPlayerAndGame());
        displayPlayerButton.setOnAction(e -> displayPlayers());
        displayGameButton.setOnAction(e -> displayGames());
        displayPlayerAndGameButton.setOnAction(e -> displayPlayerAndGame());

        loadPlayerIds();

        primaryStage.setScene(new Scene(layout, 1200, 800));
        primaryStage.show();
    }

    private <S, T> TableColumn<S, T> createColumn(String title, String property, Class<T> type) {
        TableColumn<S, T> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private void loadPlayerIds() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT PLAYER_ID FROM Player"; 
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            playerIdFilterComboBox.getItems().clear();
            while (rs.next()) {
                playerIdFilterComboBox.getItems().add(rs.getInt("PLAYER_ID"));
            }
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    private void insertPlayer() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Player (PLAYER_ID, FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(playerIdField.getText()));
            stmt.setString(2, firstNameField.getText());
            stmt.setString(3, lastNameField.getText());
            stmt.setString(4, addressField.getText());
            stmt.setString(5, postalCodeField.getText());
            stmt.setString(6, provinceField.getText());
            stmt.setString(7, phoneNumberField.getText());
            stmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Player inserted successfully!");
            loadPlayerIds();
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    private void updatePlayer() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Player SET FIRST_NAME = ?, LAST_NAME = ?, ADDRESS = ?, POSTAL_CODE = ?, PROVINCE = ?, PHONE_NUMBER = ? WHERE PLAYER_ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstNameField.getText());
            stmt.setString(2, lastNameField.getText());
            stmt.setString(3, addressField.getText());
            stmt.setString(4, postalCodeField.getText());
            stmt.setString(5, provinceField.getText());
            stmt.setString(6, phoneNumberField.getText());
            stmt.setInt(7, Integer.parseInt(playerIdField.getText()));
            stmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Player updated successfully!");
            loadPlayerIds();
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    private void insertGame() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Game (GAME_ID, GAME_TITLE) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(gameIdField.getText()));
            stmt.setString(2, gameTitleField.getText());
            stmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Game inserted successfully!");
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    private void insertPlayerAndGame() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO PlayerAndGame (PLAYER_GAME_ID, PLAYER_ID, GAME_ID, PLAYING_DATE, SCORE) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(playerGameIdField.getText()));
            stmt.setInt(2, Integer.parseInt(playerIdField.getText()));
            stmt.setInt(3, Integer.parseInt(gameIdField.getText()));
            stmt.setDate(4, Date.valueOf(playingDateField.getText()));
            stmt.setInt(5, Integer.parseInt(scoreField.getText()));
            stmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Player and Game relation inserted successfully!");
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    private void displayPlayers() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Player";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            playerTable.getItems().clear();
            while (rs.next()) {
                playerTable.getItems().add(new Player(
                        rs.getInt("PLAYER_ID"),
                        rs.getString("FIRST_NAME"),
                        rs.getString("LAST_NAME"),
                        rs.getString("ADDRESS"),
                        rs.getString("POSTAL_CODE"),
                        rs.getString("PROVINCE"),
                        rs.getString("PHONE_NUMBER")
                ));
            }
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    private void displayGames() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Game";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            gameTable.getItems().clear();
            while (rs.next()) {
                gameTable.getItems().add(new Game(
                        rs.getInt("GAME_ID"),
                        rs.getString("GAME_TITLE")
                ));
            }
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    private void displayPlayerAndGame() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = """
                SELECT PLAYER_GAME_ID, PLAYER_ID, GAME_ID, PLAYING_DATE, SCORE 
                FROM PlayerAndGame
            """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            playerAndGameTable.getItems().clear();
            while (rs.next()) {
                playerAndGameTable.getItems().add(new PlayerAndGame(
                        rs.getInt("PLAYER_GAME_ID"),
                        rs.getInt("PLAYER_ID"),
                        rs.getInt("GAME_ID"),
                        rs.getDate("PLAYING_DATE").toString(),
                        rs.getInt("SCORE")
                ));
            }
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
