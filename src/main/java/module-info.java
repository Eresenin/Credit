module com.lpnu.vasyliev.credit {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires slf4j.api;
    requires java.mail;

    opens com.lpnu.vasyliev.credit to javafx.fxml;
    opens com.lpnu.vasyliev.credit.entity to javafx.fxml;
    opens com.lpnu.vasyliev.credit.controller.menu.command to javafx.fxml;
    exports com.lpnu.vasyliev.credit.controller.menu.command;
    exports com.lpnu.vasyliev.credit;
    exports com.lpnu.vasyliev.credit.entity;
    exports com.lpnu.vasyliev.credit.controller;
    opens com.lpnu.vasyliev.credit.controller to javafx.fxml;
}