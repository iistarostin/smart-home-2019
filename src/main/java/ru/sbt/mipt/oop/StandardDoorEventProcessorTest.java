package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

class StandardDoorEventProcessorTest {

    @Test
    void process() {
        SmartHome sample = HomeBuilder.generateSmartHome();
        SmartHomeEventProcessor doorProc = new StandardDoorEventProcessor(sample, new DummyCommandSender());
        doorProc.process("1", SensorEventType.DOOR_OPEN);
        assert ((Door)(((Room)sample.getIterator().next()).getDoors().iterator().next())).isOpen();

        doorProc.process(sample.getId(), SensorEventType.DOOR_OPEN);
        sample.applyComposite(sample.getId(), new BiConsumer<SmartHomeElement, List<SmartHomeElement>>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement, List<SmartHomeElement> smartHomeElements) {
                if (smartHomeElement instanceof Door) {
                    assert ((Door) smartHomeElement).isOpen();
                }
            }
        });
    }
}