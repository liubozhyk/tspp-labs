module org.tspp.tspp_lab3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.tspp.tspp_lab3 to javafx.fxml;
    exports org.tspp.tspp_lab3;
}