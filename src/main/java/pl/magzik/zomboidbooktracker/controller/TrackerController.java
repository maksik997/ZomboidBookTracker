package pl.magzik.zomboidbooktracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.magzik.zomboidbooktracker.model.BookTableModel;
import pl.magzik.zomboidbooktracker.model.TrackerModel;

public class TrackerController {

    private final TrackerModel model;

    public TrackerController() {
        this.model = new TrackerModel();
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
        initializeLevel(levelFiveColumn, n++);

        bookTable.setItems(model.getBooks());

    }

    private void initializeLevel(TableColumn<BookTableModel, Boolean> levelColumn, int n) {
        levelColumn.setCellValueFactory(p -> p.getValue().getLevel().get(n));
        levelColumn.setCellFactory(CheckBoxTableCell.forTableColumn(levelColumn));

    }

}
