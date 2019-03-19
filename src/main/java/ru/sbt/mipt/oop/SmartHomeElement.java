package ru.sbt.mipt.oop;
import java.util.List;
import java.util.function.Consumer;
import  java.util.Iterator;

public abstract class SmartHomeElement {
    private String id;
    private SmartHomeElement parent;

    public SmartHomeElement(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    //getIterator method and its implementations are required to process composite calls in uniform manner
    public abstract Iterator<? extends SmartHomeElement> getIterator();

    public abstract void apply(Consumer<SmartHomeElement> action);

    //This is a template method!
    public void applyComposite(String id, Consumer<SmartHomeElement> action) {
        Iterator<? extends SmartHomeElement> children = getIterator();
        if (this.id.equals(id)) {
            apply(action);
            while (children.hasNext()) {
                children.next().apply(action);
            }
        }
        else {
            while (children.hasNext()) {
                children.next().applyComposite(id, action);
            }
        }
    }

    public SmartHomeElement getParent() {
        return parent;
    }

    public void setParent(SmartHomeElement parent) {
        this.parent = parent;
    }
}
