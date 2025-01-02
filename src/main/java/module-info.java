module pl.magzik.zomboidbooktracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.slf4j;
    requires java.desktop;
    requires jakarta.xml.bind;

    opens pl.magzik.zomboidbooktracker to javafx.fxml, jakarta.xml.bind;
    opens pl.magzik.zomboidbooktracker.controller to javafx.fxml, jakarta.xml.bind;
    opens pl.magzik.zomboidbooktracker.model to javafx.base, jakarta.xml.bind;

    exports pl.magzik.zomboidbooktracker;
    exports pl.magzik.zomboidbooktracker.controller;
}