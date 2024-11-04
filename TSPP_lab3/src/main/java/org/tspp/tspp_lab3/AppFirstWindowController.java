package org.tspp.tspp_lab3;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AppFirstWindowController implements Initializable {

    @FXML
    private TextArea fileTextArea;

    @FXML
    private Menu goodsAdminInfoMenu;

    @FXML
    private MenuItem goodsEditFile;

    @FXML
    private MenuItem goodsImportFile;

    @FXML
    private Menu goodsClientInfoMenu;

    @FXML
    private MenuItem goodsClientInfo;

    @FXML
    private TabPane goodsInfoTabPane;

    @FXML
    private Tab goodsInfoTab;

    @FXML
    private Tab goodsEditTab;

    @FXML
    private TableView<?> goodsTableView;

    @FXML
    private TableColumn<Equipment, String> goodsName;

    @FXML
    private TableColumn<Equipment, Double> goodsPrice;

    @FXML
    private TableColumn<Equipment, Integer> goodsAmount;

    @FXML
    private TableColumn<Equipment, Boolean> goodsAvailable;

    @FXML
    private ComboBox<String> modeChooser;

    @FXML
    private Button saveFileButton;

    @FXML
    void SelectedIndexChanged() {
        if (modeChooser.getSelectionModel().getSelectedIndex() == 0) {
            try {
                Stage passwordWindowStage = new Stage();
                FXMLLoader passwordWindowLoader = new FXMLLoader(HelloApplication.class.getResource("password-window.fxml"));
                Scene passwordWindow = new Scene(passwordWindowLoader.load(), 250, 150);
                passwordWindowStage.setTitle("Введення паролю");
                passwordWindowStage.setScene(passwordWindow);
                passwordWindowStage.show();

                AppSecondWindowController appSecondWindowController = passwordWindowLoader.getController();
                appSecondWindowController.logInButton.setOnMouseClicked(event -> {
                    if (appSecondWindowController.maskedPasswordField.getText().equals("abc123")) {
                        passwordWindowStage.close();
                        goodsAdminInfoMenu.setDisable(false);
                        goodsClientInfoMenu.setDisable(true);
                        goodsInfoTabPane.setVisible(true);
                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            goodsAdminInfoMenu.setDisable(true);
            goodsClientInfoMenu.setDisable(false);
            goodsInfoTabPane.setVisible(true);
            goodsEditTab.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goodsAdminInfoMenu.setDisable(true);
        goodsClientInfoMenu.setDisable(true);
        goodsInfoTabPane.setVisible(false);
        modeChooser.setItems(FXCollections.observableArrayList("Режим роботи адміністратора", "Режим роботи клієнта"));
    }

    public void saveFile() {

    }


    public void importFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        Stage stage = (Stage) fileTextArea.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                fileTextArea.setText(sb.toString());
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        }

    }

}
