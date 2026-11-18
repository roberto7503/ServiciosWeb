module org.example.serviciosweb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens org.example.serviciosweb to javafx.fxml;
    opens org.example.serviciosweb.controller to javafx.fxml;
    opens org.example.serviciosweb.model to javafx.base;

    exports org.example.serviciosweb;
    exports org.example.serviciosweb.model;
    exports org.example.serviciosweb.service;
}
