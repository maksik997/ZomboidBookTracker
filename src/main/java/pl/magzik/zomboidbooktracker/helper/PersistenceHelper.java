package pl.magzik.zomboidbooktracker.helper;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import pl.magzik.zomboidbooktracker.base.PathResolver;
import pl.magzik.zomboidbooktracker.model.BookDTO;
import pl.magzik.zomboidbooktracker.model.BookListDTO;
import pl.magzik.zomboidbooktracker.model.BookTableModel;

import java.nio.file.Path;
import java.util.List;

public class PersistenceHelper {

    private static final String DATA_FILE = "data.xml";

    private final Path dataPath;

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public PersistenceHelper() {
        this.dataPath = PathResolver.getInstance()
                                    .getDataDirectory()
                                    .resolve(DATA_FILE);

        try {
            JAXBContext context = JAXBContext.newInstance(BookListDTO.class);
            this.marshaller = context.createMarshaller();
            this.unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportData(List<BookTableModel> books) throws JAXBException {
        // TODO...
    }

    public void saveData(List<BookTableModel> books) throws JAXBException {
        BookListDTO bookListDTO = new BookListDTO(
            books.stream()
                  .map(b -> new BookDTO(b.getName(), b.getUnboxedLevels()))
                  .toList()
        );

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(bookListDTO, dataPath.toFile());
    }

    public List<BookTableModel> loadData() throws JAXBException {
        BookListDTO bookListDTO = (BookListDTO) unmarshaller.unmarshal(dataPath.toFile());

        /* TODO: VALIDATE THIS DATA MAYBE? */

        return bookListDTO.getBooks().stream()
                .map(bookDTO -> new BookTableModel(bookDTO.getName(), bookDTO.getLevels()))
                .toList();
    }

    public List<BookTableModel> importData() throws JAXBException {
//        TODO: ...
        return null;
    }

}
