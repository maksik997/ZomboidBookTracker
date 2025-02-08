package pl.magzik.zomboidbooktracker.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    protected void showErrorAlert(String logMessage, String headerText, String contextText) {
        log.error("{}", logMessage);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error:");
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    protected String showInputDialog(String title, String header, String context) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(context);
        return dialog.showAndWait().orElse("");
    }
}
