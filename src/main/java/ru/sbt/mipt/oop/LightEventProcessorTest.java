package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class LightEventProcessorTest {

    @Test
    void process() {
        SmartHome sample = HomeBuilder.generateSmartHome();
        LightEventProcessor lightProc = new LightEventProcessor(sample);
        lightProc.turnLightOff("2");
        sample.applyComposite(sample.getId(), new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement.getParent().getId() == "kitchen" && smartHomeElement instanceof Light) {
                    assert !((Light) smartHomeElement).isOn();
                }
            }
        });

        lightProc.turnLightOn(sample.getId());
        sample.applyComposite(sample.getId(), new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement instanceof Light) {
                    assert ((Light) smartHomeElement).isOn();
                }
            }
        });
    }
}