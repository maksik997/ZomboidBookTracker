package pl.magzik.zomboidbooktracker.helper;

import jakarta.xml.bind.*;
import org.xml.sax.SAXException;
import pl.magzik.zomboidbooktracker.base.PathResolver;
import pl.magzik.zomboidbooktracker.model.BookDTO;
import pl.magzik.zomboidbooktracker.model.BookListDTO;
import pl.magzik.zomboidbooktracker.model.BookTableModel;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

public class PersistenceHelper {

    private static final String DATA_FILE = "data.xml";

    private static final URL SCHEMA = PersistenceHelper.class.getResource("/books.xsd");

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

            Schema schema = loadSchema();

            marshaller.setSchema(schema);
            unmarshaller.setSchema(schema);
        } catch (JAXBException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookTableModel> loadData() throws JAXBException {
        if (!validateFile(dataPath.toFile())) return List.of();

        BookListDTO bookListDTO = (BookListDTO) unmarshaller.unmarshal(dataPath.toFile());

        return unpackData(bookListDTO);
    }

    public List<BookTableModel> importData(File file) throws JAXBException {
        if (!validateFile(file)) throw new JAXBException("File doesn't exits or is not a file.");

        BookListDTO bookListDTO = (BookListDTO) unmarshaller.unmarshal(file);

        return unpackData(bookListDTO);
    }

    public void saveData(List<BookTableModel> books) throws JAXBException {
        marshaller.marshal(packData(books), dataPath.toFile());
    }

    public void exportData(List<BookTableModel> books, File file) throws JAXBException {
        if (file.exists() && !file.isFile()) throw new JAXBException("File is not a file.");

        marshaller.marshal(packData(books), file);
    }

    private Schema loadSchema() throws SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return schemaFactory.newSchema(PersistenceHelper.SCHEMA);
    }

    private boolean validateFile(File file) {
        return file.exists() && file.isFile();
    }

    private BookListDTO packData(List<BookTableModel> books) throws PropertyException {
        BookListDTO bookListDTO = new BookListDTO(
                books.stream()
                        .map(b -> new BookDTO(b.getName(), b.getUnboxedLevels()))
                        .toList()
        );

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return bookListDTO;
    }

    private List<BookTableModel> unpackData(BookListDTO bookListDTO) {
        if (bookListDTO.getBooks() == null) return null;

        return bookListDTO.getBooks().stream()
                .map(bookDTO -> new BookTableModel(bookDTO.getName(), bookDTO.getLevels()))
                .toList();
    }

}
