package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class AlarmEventProcessorTest {

    @Test
    void deactivateAlarm() {
        SmartHome sample = HomeBuilder.generateSmartHome();
        AlarmEventProcessor alarmEventProcessor = new AlarmEventProcessor(sample);
        alarmEventProcessor.activateAlarm("alarm", "123456");
        sample.applyComposite("alarm", new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                assert ((Alarm)smartHomeElement).isActive();
            }
        });
        alarmEventProcessor.deactivateAlarm("alarm", "123456");
        sample.applyComposite("alarm", new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                assert ((Alarm)smartHomeElement).isInactive();
            }
        });
        alarmEventProcessor.activateAlarm("alarm", "654321");
        alarmEventProcessor.deactivateAlarm("alarm", "123456");
        sample.applyComposite("alarm", new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                assert ((Alarm)smartHomeElement).isRaised();
            }
        });
    }
}