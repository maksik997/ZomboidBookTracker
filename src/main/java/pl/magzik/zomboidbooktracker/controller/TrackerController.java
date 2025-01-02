package pl.magzik.zomboidbooktracker.controller;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.zomboidbooktracker.model.BookTableModel;
import pl.magzik.zomboidbooktracker.model.TrackerModel;
import pl.magzik.zomboidbooktracker.service.TrackerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrackerController {

    private static final Logger log = LoggerFactory.getLogger(TrackerController.class);

    private final TrackerModel model;

    private final TrackerService service;

    public TrackerController() {
        this.model = new TrackerModel();
        this.service = new TrackerService(model);
    }

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

        // TODO: TEMP
        model.getBooks().add(new BookTableModel("ABCD", true, false, true, false, true));

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
    }

    @FXML
    public void handleRemove() {
        List<BookTableModel> list = model.getBooks().filtered(BookTableModel::isSelected);
        service.removeBooks(list);
    }

    public void handleImport() {

    }

    public void handleExport() {

    }

    public void handleSearch() {

    }

    public void handleTableUpdate() {

    }

    private void updateElementCount() {

    }

    /* TODO: CHANGE THIS METHOD TO ATTACH CALLBACK LISTENER WHEN USER CLICK CHECKBOX  */
    private void initializeLevel(TableColumn<BookTableModel, Boolean> levelColumn, int n) {
        levelColumn.setCellValueFactory(p -> p.getValue().getLevel().get(n));
        levelColumn.setCellFactory(CheckBoxTableCell.forTableColumn(levelColumn));
    }

}
