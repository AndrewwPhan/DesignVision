module com.cv.designvision {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.cv.designvision to javafx.fxml;
    exports com.cv.designvision;
    exports com.cv.designvision.controllers;
    opens com.cv.designvision.controllers to javafx.fxml;
    exports com.cv.designvision.models;
    opens com.cv.designvision.models to javafx.fxml;
    exports com.cv.designvision.controllers.panels;
    opens com.cv.designvision.controllers.panels to javafx.fxml;
    exports com.cv.designvision.models.patch;
    opens com.cv.designvision.models.patch to javafx.fxml;
    exports com.cv.designvision.Services;
    opens com.cv.designvision.Services to javafx.fxml;
}