package pl.magzik.zomboidbooktracker.service;

import pl.magzik.zomboidbooktracker.model.BookTableModel;
import pl.magzik.zomboidbooktracker.model.TrackerModel;

public class TrackerService {

    private final TrackerModel model;

    public TrackerService(TrackerModel model) {
        this.model = model;
    }

    /**
     * This method should: add new book to the model class.
     * */
    public void addBook(String name) {
        BookTableModel book = new BookTableModel(name);
        model.getBooks().add(book);
    }
// PLEASE @TCPJaglak implement this method.
}
