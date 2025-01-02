package pl.magzik.zomboidbooktracker.controller;

import jakarta.xml.bind.JAXBException;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.zomboidbooktracker.model.BookTableModel;
import pl.magzik.zomboidbooktracker.model.TrackerModel;
import pl.magzik.zomboidbooktracker.service.TrackerService;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class TrackerController {

    private static final Logger log = LoggerFactory.getLogger(TrackerController.class);

    private final TrackerModel model;

    private final TrackerService service;

    public TrackerController() {
        this.model = new TrackerModel();
        this.service = new TrackerService(model);
    }

    @FXML
    private Text bookCountText;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<BookTableModel> bookTable;

    @FXML
    private TableColumn<BookTableModel, Boolean> selectColumn;

    @FXML
    private TableColumn<BookTableModel, String> nameColumn;

    @FXML
    private TableColumn<BookTableModel, Boolean> levelOneColumn;

    @FXML
    private TableColumn<BookTableModel, Boolean> levelTwoColumn;

    @FXML
    private TableColumn<BookTableModel, Boolean> levelThreeColumn;

    @FXML
    private TableColumn<BookTableModel, Boolean> levelFourColumn;

    @FXML
    private TableColumn<BookTableModel, Boolean> levelFiveColumn;

    @FXML
    public void initialize() {
        handleLoad();

        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        bookTable.setEditable(true);

        selectColumn.setCellValueFactory(p -> p.getValue().selectedProperty());
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        int n = 0;
        initializeLevel(levelOneColumn, n++);
        initializeLevel(levelTwoColumn, n++);
        initializeLevel(levelThreeColumn, n++);
        initializeLevel(levelFourColumn, n++);
        initializeLevel(levelFiveColumn, n);

        bookTable.setItems(model.getBooks());

        updateElementCount();
    }

    /* TODO: ADD LOCALIZATION :P */
    @FXML
    public void handleAdd() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter a book name:");
        dialog.setHeaderText("Enter a book name pwetty please:");
        dialog.setContentText("Book name:");

        Optional<String> result = dialog.showAndWait();

        if (result.isEmpty()) return;
        String name = result.get();

        if (model.getBooks().contains(new BookTableModel(name))) {
            return;
        }
        if (!name.matches("^\\w+$")) return;

        service.addBook(name);

        updateElementCount();
        handleSave();
    }

    @FXML
    public void handleRemove() {
        List<BookTableModel> list = model.getBooks().filtered(BookTableModel::isSelected);
        service.removeBooks(list);

        updateElementCount();
        handleSave();
    }

    @FXML
    public void handleImport() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null); // TODO: SHOULD BE STAGE

        if (file == null) {
            log.warn("File not selected.");
            return;
        }

        log.info("Selected file: {}", file);

        try {
            service.importBooks(file);
        } catch (JAXBException e) {
            showErrorAlert(
            "Couldn't import books from file" + file + " because: " + e,
            "Couldn't import books from file.",
            "Bad format."
            );
        }

        updateElementCount();
        handleSave();
    }

    @FXML
    public void handleExport() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null); // TODO: SHOULD BE A STAGE.

        if (file == null) {
            log.warn("Directory not selected.");
            return;
        }

        log.info("Selected directory: {}", file);

        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText("Enter file name:");
        textInputDialog.setContentText("Please :)");
        Optional<String> result = textInputDialog.showAndWait();

        if (result.isPresent()) {
            file = Paths.get(file.toString(), result.get() + ".xml").toFile();
            try {
                service.exportBooks(file);
            } catch (JAXBException e) {
                showErrorAlert(
                    "Couldn't export data from file, because: " + e,
                    "Couldn't export data.",
                    "Because: " + e.getMessage()
                );
            }
        }
    }

    @FXML
    public void handleSearch() {
        String key = searchTextField.getText();

        if (key.isBlank()) {
            bookTable.setItems(model.getBooks());
            return;
        }

        ObservableList<BookTableModel> list = model.getBooks()
                .filtered(b -> b.getName().contains(key));
        bookTable.setItems(list);
    }

    private void updateElementCount() {
        int size = model.getBooks().size();
        bookCountText.setText(String.valueOf(size));
    }

    private void handleSave() {
        try {
            service.saveBooks();
        } catch (JAXBException e) {
            showErrorAlert(
                "Couldn't save data, because: " + e,
                "Couldn't save data.",
                "Because: " + e.getMessage()
            );
        }
    }

    private void handleLoad() {
        try {
            service.loadBooks();
        } catch (JAXBException e) {
            showErrorAlert(
                "Couldn't load data, because: " + e,
                "Couldn't load data.",
                "Because: " + e.getMessage()
            );
        }

    }

    private void showErrorAlert(String logMessage, String headerText, String contextText) {
        log.error("{}", logMessage);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error:");
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    private void initializeLevel(TableColumn<BookTableModel, Boolean> levelColumn, int n) {
        levelColumn.setCellValueFactory(p -> p.getValue().getLevels().get(n));
        levelColumn.setCellFactory(CheckBoxTableCell.forTableColumn(levelColumn));

        levelColumn.setCellFactory(column -> {
            CheckBoxTableCell<BookTableModel, Boolean> cell = new CheckBoxTableCell<>();

            cell.setSelectedStateCallback(idx -> {
                BookTableModel book = column.getTableView().getItems().get(idx);
                BooleanProperty property = book.getLevels().get(n);

                property.addListener((obs, oldValue, newValue) -> handleSave());

                return property;
            });

            return cell;
        });

    }

}
