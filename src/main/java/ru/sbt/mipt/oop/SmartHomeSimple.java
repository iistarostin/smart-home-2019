package ru.sbt.mipt.oop;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

public class SmartHomeSimple extends SmartHomeElement {
    public SmartHomeSimple(String id) {
        super(id);
    }

    @Override
    public Iterator<? extends SmartHomeElement> getIterator() {
        return Collections.emptyIterator();
    }

    @Override
    public void apply(BiConsumer<SmartHomeElement, List<SmartHomeElement>> action, List<SmartHomeElement> parents) {
        action.accept(this, parents);
    }
}
