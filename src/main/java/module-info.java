module com.example.cryptor {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires lombok;


    opens com.example.cryptor to javafx.fxml;
    exports com.example.cryptor;
}