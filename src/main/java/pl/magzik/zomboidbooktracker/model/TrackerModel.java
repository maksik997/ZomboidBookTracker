package pl.magzik.zomboidbooktracker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TrackerModel {

    private final ObservableList<BookTableModel> books;

    public TrackerModel() {
        this.books = FXCollections.observableArrayList();
    }

    public ObservableList<BookTableModel> getBooks() {
        return books;
    }
}
