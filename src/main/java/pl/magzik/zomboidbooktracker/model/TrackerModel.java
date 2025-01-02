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
        books.clear();

        if (list == null) return;
        books.addAll(list);
    }
}
