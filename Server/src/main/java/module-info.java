module Server {
    requires java.rmi;
    requires javafx.base;
    requires javafx.controls;
    requires java.naming;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires MaterialFX;
    requires mysql.connector.j;
    opens com.chatup;
}