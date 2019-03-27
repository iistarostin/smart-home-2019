package ru.sbt.mipt.oop;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public abstract class SmartHomeComposite extends SmartHomeElement {
    public SmartHomeComposite(String id) {
        super(id);
    }

    @Override
    public void apply(Consumer<SmartHomeElement> action) {
        return;
    }
}
