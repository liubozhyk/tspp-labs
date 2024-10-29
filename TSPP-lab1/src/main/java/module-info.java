module org.tspp.tspplab1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;


    opens org.tspp.tspplab1 to javafx.fxml;
    exports org.tspp.tspplab1;
}