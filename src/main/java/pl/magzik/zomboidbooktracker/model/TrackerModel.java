package pl.magzik.zomboidbooktracker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;

public class TrackerModel {

    private final ObservableList<BookTableModel> books;

    public TrackerModel() {
        this.books = FXCollections.observableArrayList();
    }

    public ObservableList<BookTableModel> getBooks() {
        return books;
    }

    public void clearAndAddAll(Collection<BookTableModel> list) {
        /* TODO: @TCPJaglak,
        *   1. Clear books list,
        *   2. Add all elements from given list.
        *   3. If given list is null, clear books list.*/
    }
}
