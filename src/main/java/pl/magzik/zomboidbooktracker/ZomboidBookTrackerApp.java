package pl.magzik.zomboidbooktracker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.zomboidbooktracker.controller.Controller;

import java.net.URL;

public class ZomboidBookTrackerApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(ZomboidBookTrackerApp.class);

    private static final String VIEW_PATH = "/fxml/tracker.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        log.info("Initializing Application...");

        FXMLLoader loader = loadView();
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        ((Controller) loader.getController()).setStage(stage);

        URL stylesUrl = getClass().getResource("/styles.css");
        assert stylesUrl != null;
        scene.getStylesheets().add(stylesUrl.toExternalForm());

        stage.setTitle("Zomboid Book Tracker");
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        log.info("Application has started.");
    }

    private FXMLLoader loadView() {
        URL viewUrl = getClass().getResource(VIEW_PATH);

        return new FXMLLoader(viewUrl);
    }
}
