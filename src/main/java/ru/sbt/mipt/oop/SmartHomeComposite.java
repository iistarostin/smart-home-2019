package ru.sbt.mipt.oop;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class SmartHomeComposite extends SmartHomeElement {
    public SmartHomeComposite(String id) {
        super(id);
    }

    @Override
    public void apply(BiConsumer<SmartHomeElement, List<SmartHomeElement>> action, List<SmartHomeElement> parents) {
        return;
    }
}
