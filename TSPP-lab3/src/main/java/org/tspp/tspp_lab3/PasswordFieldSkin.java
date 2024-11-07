package org.tspp.tspp_lab3;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;

public class PasswordFieldSkin extends TextFieldSkin {

    public PasswordFieldSkin(TextField textField) {
        super(textField);
    }

    @Override
    protected String maskText(String var1) {
        if (!(this.getSkinnable() instanceof PasswordField)) {
            return var1;
        } else {
            int var2 = var1.length();
            StringBuilder var3 = new StringBuilder(var2);
            for(int var4 = 0; var4 < var2; ++var4) {
                char ch = var1.charAt(var4);
                if (Character.isLetter(ch)) {
                    var3.append('a');
                } else if (Character.isDigit(ch)) {
                    var3.append('0');
                } else {
                    var3.append('●');
                }
            }
            return var3.toString();
        }
    }
}
