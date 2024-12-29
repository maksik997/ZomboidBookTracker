module pl.magzik.zomboidbooktracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.slf4j;

    opens pl.magzik.zomboidbooktracker to javafx.fxml;
    opens pl.magzik.zomboidbooktracker.controller to javafx.fxml;
    opens pl.magzik.zomboidbooktracker.model to javafx.base;

    exports pl.magzik.zomboidbooktracker;
    exports pl.magzik.zomboidbooktracker.controller;
}