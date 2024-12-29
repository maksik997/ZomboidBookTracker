package pl.magzik.zomboidbooktracker.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

public class BookTableModel {

    private final BooleanProperty selected;

    private final StringProperty name;

    private final ObservableList<BooleanProperty> level;

    public BookTableModel(String name, Boolean... levels) {
        if (levels.length != 5) throw new IllegalArgumentException("Level length must be 5");

        this.selected = new SimpleBooleanProperty();
        this.name = new SimpleStringProperty(name);
        this.level = FXCollections.observableArrayList();
        level.addAll(
            Arrays.stream(levels).map(SimpleBooleanProperty::new).toList()
        );
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObservableList<BooleanProperty> getLevel() {
        return level;
    }
}
