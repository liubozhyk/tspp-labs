package org.tspp.tspp_lab2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private Button addClientButton;

    @FXML
    private Button addItemButton;

    @FXML
    private ListView<String> bookListView;

    @FXML
    private Spinner<Book> domainSpinner;

    @FXML
    private Label idLabel;

    @FXML
    private RadioButton newClientRadioButton;

    @FXML
    private TextField newClientTextField;

    @FXML
    private Spinner<Integer> numericSpinner;

    @FXML
    private ComboBox<Client> regularClientComboBox;

    @FXML
    private RadioButton regularClientRadioButton;

    @FXML
    private TextArea resultTextArea;

    public float totalPrice;

    public ObservableList<Book> books = FXCollections.observableArrayList(
            new Book("1984","Джордж Оруелл", 67.90f),
            new Book("Дюна","Френка Герберта", 76.49f),
            new Book("Хроніки Нарнії","К.С. Льюїс", 34.79f),
            new Book("Людина в пошуках сенсу", "Віктор Франкл",99.90f)
    );

    public ObservableList<Client> clients = FXCollections.observableArrayList(
            new Client("Ivan"),
            new Client("John"),
            new Client("Philip"),
            new Client("Gerald")
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,30);
        SpinnerValueFactory<Book> bookSpinnerValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(books);
        numericSpinner.setValueFactory(integerSpinnerValueFactory);
        domainSpinner.setValueFactory(bookSpinnerValueFactory);
        bookListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        regularClientComboBox.getItems().addAll(clients);
    }

    @FXML
    void forRadioButtons(MouseEvent event) {
        if (regularClientRadioButton.isSelected()) {
            regularClientComboBox.setDisable(false);
            newClientTextField.setDisable(true);
        } else {
            regularClientComboBox.setDisable(true);
            newClientTextField.setDisable(false);
        }
    }

    @FXML
    void addClientToTextArea (MouseEvent event) {
        if (regularClientRadioButton.isSelected()) {
            resultTextArea.setText("Client:\t" + regularClientComboBox.getSelectionModel().getSelectedItem().toString() +
                    "\nClient ID:\t" + "ID_" + regularClientComboBox.getSelectionModel().getSelectedItem().getId() + "\n");
            for(String book : bookListView.getItems()) {
                resultTextArea.appendText(book + "\n");
            }
            resultTextArea.appendText("\nTotal price: " + totalPrice + "$");
        } else {
            regularClientComboBox.getItems().add(new Client(newClientTextField.getText()));
            resultTextArea.setText("Client:\t" + regularClientComboBox.getItems().getLast().getName() +
                    "\nClient ID:\t" + "ID_" + regularClientComboBox.getItems().getLast().getId() + "\n");
            for(String book : bookListView.getItems()) {
                resultTextArea.appendText(book + "\n");
            }
            resultTextArea.appendText("\nTotal price: " + totalPrice + "$");
        }
    }


    @FXML
    void saveToFile(KeyEvent event) {
        String selectedClient = regularClientRadioButton.isSelected() ? regularClientComboBox.getSelectionModel().getSelectedItem().getName() : newClientTextField.getText();
        if (event.isControlDown() && event.getCode() == KeyCode.S) {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setHeaderText("Збереження");
            alertConfirm.setContentText("Зберегти файл?");

            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try (FileWriter fileWriter = new FileWriter("Saved File of " + selectedClient + ".txt")) {
                    fileWriter.write(resultTextArea.getText());
                } catch (IOException e) {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setHeaderText("Помилка збереження");
                    alertError.setContentText("Помилка під час збереження файлу: " + e.getMessage());
                    alertError.show();
                }
                Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setHeaderText("Збережено");
                alertSuccess.setContentText("Файл успішно збережено!");
                alertSuccess.show();
            }
        }
    }

    @FXML
    void openHelp(KeyEvent event) {
        if (event.getCode() == KeyCode.F1) {
            String resourcePath = "/org/tspp/tspp_lab2/Help.txt"; // Resource path
            URL resourceUrl = getClass().getResource(resourcePath);

            if (resourceUrl != null) {
                try {
                    File helpFile = new File(resourceUrl.toURI());
                    Desktop.getDesktop().open(helpFile);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Help file not found: " + resourcePath);
            }
        }
    }

    @FXML
    void cleanTextArea() {
        resultTextArea.clear();
    }

    @FXML
    void setLabelFromSelectedComboBox(ActionEvent event) {
        idLabel.setText("ID_" + regularClientComboBox.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    void addToListView(MouseEvent event) {
        bookListView.getItems().add(domainSpinner.getValue() + "\t\t" + numericSpinner.getValue().toString());
        totalPrice += numericSpinner.getValue() * domainSpinner.getValue().getPrice();
    }

    @FXML
    void deleteItems (KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE || (event.isShiftDown() && event.getCode() == KeyCode.D)) {
            bookListView.getItems().removeAll(bookListView.getSelectionModel().getSelectedItems());
        }
    }





}
