package pl.magzik.zomboidbooktracker.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class BookDTO {

    private String name;
    private List<Boolean> levels;

    public BookDTO() {}

    public BookDTO(String name, List<Boolean> levels) {
        this.name = name;
        this.levels = levels;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public List<Boolean> getLevels() {
        return levels;
    }

    public void setLevels(List<Boolean> levels) {
        this.levels = levels;
    }
}
