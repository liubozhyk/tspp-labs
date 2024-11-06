module org.tspp.tspp_lab2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens org.tspp.tspp_lab2 to javafx.fxml;
    exports org.tspp.tspp_lab2;
}