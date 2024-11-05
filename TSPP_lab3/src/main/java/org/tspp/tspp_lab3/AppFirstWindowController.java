package org.tspp.tspp_lab3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
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
    private MenuItem goodsEditFile;

    @FXML
    private Menu goodsEditFileMenu;

    @FXML
    private Tab goodsEditTab;

    @FXML
    private MenuItem goodsImportFile;

    @FXML
    private Menu goodsImportFileMenu;

    @FXML
    private Tab goodsInfoTab;

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

    @FXML
    private Button saveFileButton;

    private ObservableList<Equipment> equipments = FXCollections.observableArrayList();

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

        goodsName.setCellValueFactory(new PropertyValueFactory<Equipment, String>("name"));
        goodsName.setCellFactory(TextFieldTableCell.forTableColumn());
        goodsName.setOnEditCommit(event -> {
            Equipment equipment = event.getRowValue();
            equipment.setName(event.getNewValue());
        });

        goodsPrice.setCellValueFactory(new PropertyValueFactory<Equipment, Double>("price"));
        goodsPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        goodsPrice.setOnEditCommit(event -> {
            Equipment equipment = event.getRowValue();
            equipment.setPrice(event.getNewValue());
        });

        goodsQuantity.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("quantity"));
        goodsQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        goodsQuantity.setOnEditCommit(event -> {
            Equipment equipment = event.getRowValue();
            equipment.setQuantity(event.getNewValue());
        });
/*
        goodsCheckboxAvailability.setCellValueFactory(new PropertyValueFactory<Equipment, CheckBox>("availableCheckBox"));
        goodsCheckboxAvailability.setCellFactory(new CheckBoxTableCell.forTableColumn();

        go.setCellFactory(column -> {
            return new TableCell<Equipment, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);

                    } else {
                        CheckBox checkBox = new CheckBox();
                        checkBox.setSelected(item);
                        checkBox.setDisable(true);
                        // Відключаємо можливість редагування
                        setGraphic(checkBox);
                    }
                }
            };
        });*/
    }

    public void saveFile() {
        /*try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            // Записуємо заголовки колонок
            for (TableColumn<T, ?> column : tableView.getColumns()) {
                writer.write(column.getText() + "\t");
            }
            writer.newLine();

            // Записуємо дані рядків таблиці
            ObservableList<T> rows = tableView.getItems();
            for (T row : rows) {
                for (TableColumn<T, ?> column : tableView.getColumns()) {
                    Object cellValue = column.getCellData(row);
                    writer.write((cellValue != null ? cellValue.toString() : "") + "\t");
                }
                writer.newLine();
            }

            System.out.println("Дані успішно збережено у файл: " + fileName);

        } catch (IOException e) {
            System.out.println("Помилка при записі у файл: " + e.getMessage());
        }*/
    }

    public void importFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        if (modeChooser.getSelectionModel().getSelectedIndex() == 0) {
            File selectedFile = fileChooser.showOpenDialog(fileTextArea.getScene().getWindow());

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
        } else {
            File file = fileChooser.showOpenDialog(goodsTableView.getScene().getWindow());

            if (file != null) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(", ");
                        String name = data[0].trim();
                        double price = Double.parseDouble(data[1].trim());
                        int amount = Integer.parseInt(data[2].trim());

                        equipments.add(new Equipment(name, price, amount));
//                        goodsTableView.getItems().addAll(equipments);
                        loadEquipmentsFromFile();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void loadEquipmentsFromFile() {
        goodsTableView.getItems().add(new Equipment("rff", 6.0, -6));
        goodsTableView.getItems().add(new Equipment("tgyth", 34.0, 12));
    }

}
