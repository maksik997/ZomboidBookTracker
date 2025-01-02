package pl.magzik.zomboidbooktracker.service;

import pl.magzik.zomboidbooktracker.model.BookTableModel;
import pl.magzik.zomboidbooktracker.model.TrackerModel;

import java.util.List;

public class TrackerService {

    private final TrackerModel model;

    public TrackerService(TrackerModel model) {
        this.model = model;
    }

    public void addBook(String name) {
        BookTableModel book = new BookTableModel(name);
        model.getBooks().add(book);
    }

    /* TODO: IMPLEMENT BOOK REMOVER @TCPJag lak */
    public void removeBooks(List<BookTableModel> list) {

    }
}
