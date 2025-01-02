package pl.magzik.zomboidbooktracker.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class BookListDTO {

    private List<BookDTO> books;

    public BookListDTO() {}

    public BookListDTO(List<BookDTO> books) {
        this.books = books;
    }

    @XmlElement(name = "book")
    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
