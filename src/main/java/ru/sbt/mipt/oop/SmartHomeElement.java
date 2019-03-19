package ru.sbt.mipt.oop;
import java.util.List;
import java.util.function.BiConsumer;
import  java.util.Iterator;

public abstract class SmartHomeElement {
    private String id;

    public SmartHomeElement(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public abstract Iterator<? extends SmartHomeElement> getIterator();
    public abstract void apply(BiConsumer<SmartHomeElement, List<SmartHomeElement>> action, List<SmartHomeElement> parents);
    public void applyComposite(String id, BiConsumer<SmartHomeElement, List<SmartHomeElement>> action, List<SmartHomeElement> parents) {
        Iterator<? extends SmartHomeElement> children = getIterator();
        if (this.id.equals(id)) {
            apply(action, parents);
            parents.add(this);
            while (children.hasNext()) {
                children.next().apply(action, parents);
            }
            parents.remove(parents.size() - 1);
        }
        else {
            parents.add(this);
            while (children.hasNext()) {
                children.next().applyComposite(id, action, parents);
            }
            parents.remove(parents.size() - 1);
        }
    }
}
