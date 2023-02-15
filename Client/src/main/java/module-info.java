module Client {
    requires java.rmi;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires MaterialFX;
    requires chatter.bot.api;
    opens com.chatup;
}