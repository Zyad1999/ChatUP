module Client  {
    requires javafx.controls;
    requires javafx.fxml;
   // requires org.glavo.materialfx.adapter;

    opens com.chatup to javafx.fxml;
    exports com.chatup;

}