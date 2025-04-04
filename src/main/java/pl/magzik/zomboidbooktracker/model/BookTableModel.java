package pl.magzik.zomboidbooktracker.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BookTableModel {

    private final BooleanProperty selected;

    private final StringProperty name;

    private final ObservableList<BooleanProperty> levels;

    public BookTableModel(String name, List<Boolean> levels) {
        if (levels.size() != 5) throw new IllegalArgumentException("Level length must be 5");

        this.selected = new SimpleBooleanProperty();
        this.name = new SimpleStringProperty(name);
        this.levels = FXCollections.observableArrayList();
        this.levels.addAll(levels.stream().map(SimpleBooleanProperty::new).toList());
    }

    public BookTableModel(String name, Boolean... levels) {
        this(name, Arrays.asList(levels));
    }

    public BookTableModel(String name) {
        this(name, false, false, false, false, false);
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

    public ObservableList<BooleanProperty> getLevels() {
        return levels;
    }

    public List<Boolean> getUnboxedLevels() {
        return levels.stream()
                      .map(ObservableBooleanValue::get)
                      .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookTableModel that = (BookTableModel) o;
        return Objects.equals(name.get(), that.name.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.get());
    }
}

