module org.tspp.tspp_lab5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.tspp.tspp_lab5 to javafx.fxml;
    exports org.tspp.tspp_lab5;
}