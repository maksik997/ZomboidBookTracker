package pl.magzik.zomboidbooktracker.controller;

import jakarta.xml.bind.JAXBException;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class TrackerController extends Controller {

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
        log.info("Initializing Tracker...");
        handleLoad();

        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        bookTable.setEditable(true);
        bookTable.setSelectionModel(null);
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

        log.info("Tracker initialized.");
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

    @FXML
    public void handleAdd() {
        String name = showInputDialog("Enter a book name pwetty please:", "Book name:");
        if (name.isEmpty() || !name.matches("^[\\d\\p{L} ]+$")) return;
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
        File file = fileChooser.showOpenDialog(getStage());
        if (isFileNotSelected(file)) return;

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
        File file = directoryChooser.showDialog(getStage());
        if (isFileNotSelected(file)) return;

        String filename = showInputDialog("Enter file name:", "Please :)");

        if (!filename.isEmpty()) {
            file = Paths.get(file.toString(), filename + ".xml").toFile();
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

    private void updateElementCount() {
        int size = model.getBooks().size();
        bookCountText.setText(String.valueOf(size));
    }

    private boolean isFileNotSelected(File file) {
        if (file == null) log.warn("File not selected.");
        else log.info("Selected file: {}", file);
        return file == null;
    }
}
