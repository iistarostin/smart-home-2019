package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

class HallDoorEventProcessorTest {

    @Test
    void closeDoor() {
        SmartHome sample = HomeBuilder.generateSmartHome();
        HallDoorEventProcessor doorProc = new HallDoorEventProcessor(sample, new DummyCommandSender());
        doorProc.openDoor("hall");
        sample.applyComposite(sample.getId(), new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement.getParent().getId().equals("hall") && smartHomeElement instanceof Door) {
                    assert ((Door)smartHomeElement).isOpen();
                }
            }
        });
        doorProc.closeDoor("hall");
        doorProc.openDoor(sample.getId());
        sample.applyComposite(sample.getId(), new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement instanceof Light) {
                    assert !((Light) smartHomeElement).isOn();
                }
            }
        });
    }
}