package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class StandardDoorEventProcessorTest {

    @Test
    void process() {
        SmartHome sample = HomeBuilder.generateSmartHome();
        StandardDoorEventProcessor doorProc = new StandardDoorEventProcessor(sample);
        doorProc.openDoor("1");
        assert ((Door)(((Room)sample.getIterator().next()).getDoors().iterator().next())).isOpen();

        doorProc.openDoor(sample.getId());
        sample.applyComposite(sample.getId(), new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement instanceof Door) {
                    assert ((Door) smartHomeElement).isOpen();
                }
            }
        });
    }
}