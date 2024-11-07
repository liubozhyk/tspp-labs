package org.tspp.tspp_lab3;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class AppSecondWindowController implements Initializable {

    @FXML
    public Button logInButton;

    @FXML
    public PasswordField maskedPasswordField;

    public PasswordFieldSkin skin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        skin = new PasswordFieldSkin(maskedPasswordField);
        maskedPasswordField.setSkin(skin);
    }
}
