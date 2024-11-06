package org.tspp.tspp_lab3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AppFirstWindowController implements Initializable {

    @FXML
    private TextArea fileTextArea;

    @FXML
    private TableColumn<Equipment, CheckBox> goodsAvailable;

    @FXML
    private Menu goodsEditFileMenu;

    @FXML
    private Tab goodsEditTab;

    @FXML
    private Menu goodsImportFileMenu;

    @FXML
    private TabPane goodsInfoTabPane;

    @FXML
    private TableColumn<Equipment, String> goodsName;

    @FXML
    private TableColumn<Equipment, Double> goodsPrice;

    @FXML
    private TableColumn<Equipment, Integer> goodsQuantity;

    @FXML
    private TableView<Equipment> goodsTableView;

    @FXML
    private ComboBox<String> modeChooser;

    private final ObservableList<Equipment> equipments = FXCollections.observableArrayList();

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
                        goodsEditFileMenu.setDisable(false);
                        goodsImportFileMenu.setDisable(false);
                        goodsInfoTabPane.setVisible(true);
                        goodsEditTab.setDisable(false);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            goodsEditFileMenu.setDisable(true);
            goodsImportFileMenu.setDisable(false);
            goodsInfoTabPane.setVisible(true);
            goodsEditTab.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modeChooser.setItems(FXCollections.observableArrayList("Режим роботи адміністратора", "Режим роботи клієнта"));
        goodsImportFileMenu.setDisable(true);
        goodsEditFileMenu.setDisable(true);
        goodsInfoTabPane.setVisible(false);
        goodsTableView.setEditable(true);

        goodsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        goodsName.setCellFactory(TextFieldTableCell.forTableColumn());
        goodsName.setOnEditCommit(event -> {
            Equipment equipment = event.getRowValue();
            equipment.setName(event.getNewValue());
            goodsTableView.refresh();
        });

        goodsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        goodsPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        goodsPrice.setOnEditCommit(event -> {
            Equipment equipment = event.getRowValue();
            equipment.setPrice(event.getNewValue());
            goodsTableView.refresh();
        });

        goodsQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        goodsQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        goodsQuantity.setOnEditCommit(event -> {
            Equipment equipment = event.getRowValue();
            equipment.setQuantity((event.getNewValue() < 0) ? 0 : event.getNewValue());
            goodsTableView.refresh();
        });

        goodsAvailable.setCellValueFactory(new PropertyValueFactory<>("CheckBox"));
    }

    public void saveFile() {
        FileChooser fileChooserForEditSave = new FileChooser();
        fileChooserForEditSave.setTitle("Save Text File");
        fileChooserForEditSave.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooserForEditSave.showSaveDialog(goodsTableView.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                for (Equipment equipment : goodsTableView.getItems()) {
                    writer.write(equipment.getName() + ", " + equipment.getPrice() + ", " + equipment.getQuantity() + "\n");
                }
                writer.flush();
            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
            }
        }
    }

    public void editFile() {
        FileChooser fileChooserForEdit = new FileChooser();
        fileChooserForEdit.setTitle("Open Text File");
        fileChooserForEdit.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooserForEdit.showOpenDialog(goodsTableView.getScene().getWindow());

        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(", ");
                    String name = data[0].trim();
                    double price = Double.parseDouble(data[1].trim());
                    int amount = Integer.parseInt(data[2].trim());

                    equipments.add(new Equipment(name, price, amount));
                }
                goodsTableView.getItems().addAll(equipments);
            } catch (IOException e) {
                System.out.println("Error opening the file: " + e.getMessage());
            }
        }
    }

    @FXML
    public void importFile() {
        FileChooser fileChooserForImport = new FileChooser();
        fileChooserForImport.setTitle("Open Text File");
        fileChooserForImport.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooserForImport.showOpenDialog(fileTextArea.getScene().getWindow());

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
