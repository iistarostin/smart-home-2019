package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

class AdvancedDoorEventProcessorTest {

    @Test
    void closeDoor() {
        SmartHome sample = HomeBuilder.generateSmartHome();
        SmartHomeEventProcessor doorProc = new AdvancedDoorEventProcessor(sample, new DummyCommandSender());
        doorProc.process("hall", SensorEventType.DOOR_OPEN);
        sample.applyComposite(sample.getId(), new BiConsumer<SmartHomeElement, List<SmartHomeElement>>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement, List<SmartHomeElement> smartHomeElements) {
                if (smartHomeElements.get(smartHomeElements.size() - 1).getId() == "hall" && smartHomeElement instanceof Door) {
                    assert ((Door)smartHomeElement).isOpen();
                }
            }
        });
        doorProc.process("hall", SensorEventType.DOOR_CLOSED);
        doorProc.process(sample.getId(), SensorEventType.DOOR_OPEN);
        sample.applyComposite(sample.getId(), new BiConsumer<SmartHomeElement, List<SmartHomeElement>>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement, List<SmartHomeElement> smartHomeElements) {
                if (smartHomeElement instanceof Light) {
                    assert !((Light) smartHomeElement).isOn();
                }
            }
        });
    }
}