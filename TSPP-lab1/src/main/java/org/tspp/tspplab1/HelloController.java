package org.tspp.tspplab1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.controlsfx.control.CheckListView;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private ListView<Book> listBookView;

    @FXML
    private RadioButton TextColorBlueRadioButton;

    @FXML
    private RadioButton TextColorGreenRadioButton;

    @FXML
    private RadioButton TextColorRedRadioButton;

    @FXML
    private RadioButton adventuresRadioButton;

    @FXML
    private RadioButton allRadioButton;

    @FXML
    private CheckListView<Book> checkBookListView;

    @FXML
    private RadioButton fantasyRadioButton;

    @FXML
    private RadioButton psychologyRadioButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button selectButton;

    @FXML
    private ToggleGroup tgGenres;

    @FXML
    private ToggleGroup tgTextColor;

    public ObservableList<Book> books = FXCollections.observableArrayList(
            new Book("1984","Джордж Оруелл", "Психологія"),
            new Book("Дюна","Френка Герберта", "Фантастика"),
            new Book("Хроніки Нарнії","К.С. Льюїс", "Пригоди"),
            new Book("Людина в пошуках сенсу", "Віктор Франкл", "Психологія")
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkBookListView.setItems(books);
    }

    @FXML
    void HandleSelectButton(ActionEvent event) {
        listBookView.getItems().setAll(checkBookListView.getCheckModel().getCheckedItems());
        if (!allRadioButton.isSelected() && !psychologyRadioButton.isSelected() && !fantasyRadioButton.isSelected() && !adventuresRadioButton.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Помилка вибору");
            alert.setContentText("Виберіть, будь ласка, жанри");
            alert.show();
        }
    }

    @FXML
    void resetAction(ActionEvent event) {
        checkBookListView.getCheckModel().clearChecks();
    }

    @FXML
    void resetBooksRadioButtons(ActionEvent event) {
        allRadioButton.setSelected(false);
        adventuresRadioButton.setSelected(false);
        fantasyRadioButton.setSelected(false);
        psychologyRadioButton.setSelected(false);
    }

    @FXML
    void setAdventuresChecked(ActionEvent event) {
        if (adventuresRadioButton.isSelected()) {
            checkBookListView.getCheckModel().clearChecks();
            for (Book book : books) {
                if (book.getGenres().equals("Пригоди")) {
                    checkBookListView.getCheckModel().check(book);
                }
            }
        }
    }

    @FXML
    void setAllChecked(ActionEvent event) {
        if (allRadioButton.isSelected()) {
            checkBookListView.getCheckModel().clearChecks();
            for (Book book : books) {
                checkBookListView.getCheckModel().check(book);
            }
        }
    }

    @FXML
    void setFantasyChecked(ActionEvent event) {
        if (fantasyRadioButton.isSelected()) {
            checkBookListView.getCheckModel().clearChecks();
            for (Book book : books) {
                if (book.getGenres().equals("Фантастика")) {
                    checkBookListView.getCheckModel().check(book);
                }
            }
        }
    }

    @FXML
    void setPsychologyChecked(ActionEvent event) {
        if (psychologyRadioButton.isSelected()) {
            checkBookListView.getCheckModel().clearChecks();
            for (Book book : books) {
                if (book.getGenres().equals("Психологія")) {
                    checkBookListView.getCheckModel().check(book);
                }
            }
        }

    }

    @FXML
    void showSelected(KeyEvent event) {
        String selected = listBookView.getSelectionModel().getSelectedItem().toString();
        if (event.isShiftDown() && event.getCode() == KeyCode.E) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Вбираний елемент");
            alert.setContentText(selected);
            alert.show();
        }
    }

    @FXML
    void cleanList(MouseEvent event) {
        if (listBookView.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Помилка видалення");
            alert.setContentText("Ніц нема в тому ListBox");
            alert.show();
        } else {
            listBookView.getItems().clear();
        }

    }

    @FXML
    void changeTextColor(MouseEvent event) {
        listBookView.setCellFactory(new Callback<ListView<Book>, ListCell<Book>>() {
            @Override
            public ListCell<Book> call(ListView<Book> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Book book, boolean empty) {
                        super.updateItem(book, empty);
                        if (empty || book == null) {
                            setText(null);
                        } else {
                            setText(book.toString());
                            if (TextColorBlueRadioButton.isSelected()) {
                                setStyle("-fx-text-fill: blue;");
                            } else if (TextColorRedRadioButton.isSelected()) {
                                setStyle("-fx-text-fill: red;");
                            } else if (TextColorGreenRadioButton.isSelected()) {
                                setStyle("-fx-text-fill: green;");
                            } else {
                                setStyle("-fx-text-fill: black;");
                            }
                        }
                    }
                };
            }
        });
    }
}
