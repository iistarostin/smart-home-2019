package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

class LightEventProcessorTest {

    @Test
    void process() {
        SmartHome sample = HomeBuilder.generateSmartHome();
        SmartHomeEventProcessor lightProc = new LightEventProcessor(sample, new DummyCommandSender());
        lightProc.process("2", SensorEventType.LIGHT_OFF);
        sample.applyComposite(sample.getId(), new BiConsumer<SmartHomeElement, List<SmartHomeElement>>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement, List<SmartHomeElement> smartHomeElements) {
                if (smartHomeElements.get(smartHomeElements.size() - 1).getId() == "kitchen" && smartHomeElement instanceof Light) {
                    assert !((Light) smartHomeElement).isOn();
                }
            }
        });

        lightProc.process(sample.getId(), SensorEventType.LIGHT_ON);
        sample.applyComposite(sample.getId(), new BiConsumer<SmartHomeElement, List<SmartHomeElement>>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement, List<SmartHomeElement> smartHomeElements) {
                if (smartHomeElement instanceof Light) {
                    assert ((Light) smartHomeElement).isOn();
                }
            }
        });
    }
}