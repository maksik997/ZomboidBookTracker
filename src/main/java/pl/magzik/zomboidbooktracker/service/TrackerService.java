package pl.magzik.zomboidbooktracker.service;

import jakarta.xml.bind.JAXBException;
import pl.magzik.zomboidbooktracker.helper.PersistenceHelper;
import pl.magzik.zomboidbooktracker.model.BookTableModel;
import pl.magzik.zomboidbooktracker.model.TrackerModel;

import java.io.File;
import java.util.List;

public class TrackerService {

    private final TrackerModel model;

    private final PersistenceHelper persistenceHelper;

    public TrackerService(TrackerModel model) {
        this.model = model;
        this.persistenceHelper = new PersistenceHelper();
    }

    public void addBook(String name) {
        BookTableModel book = new BookTableModel(name);
        model.getBooks().add(book);
    }

    public void removeBooks(List<BookTableModel> list) {
        model.getBooks().removeAll(list);
    }

    public void saveBooks() throws JAXBException {
        persistenceHelper.saveData(model.getBooks());
    }

    public void loadBooks() throws JAXBException {
        model.clearAndAddAll(persistenceHelper.loadData()) ;
    }

    public void importBooks(File file) throws JAXBException {
        model.clearAndAddAll(persistenceHelper.importData(file)) ;
    }

    public void exportBooks(File file) throws JAXBException {
        persistenceHelper.exportData(model.getBooks(), file);
    }
}
