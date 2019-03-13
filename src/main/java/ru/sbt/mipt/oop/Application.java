package ru.sbt.mipt.oop;

import java.io.IOException;

public class Application {

    public static void main(String... args) throws IOException {
        SmartHome smartHome = new SmartHomeJSONSerializer().readSmartHome();
        applicationLoop(smartHome);
    }

    private static void applicationLoop(SmartHome smartHome) {
        // начинаем цикл обработки событий
        SensorEventReceiver sensorEventReceiver = new SensorEventGenerator();
        SensorEvent event;
        SmartHomeEventHandler eventHandler = new StandardSmartHomeEventHandler(smartHome, new DummyCommandSender());
        while ((event = sensorEventReceiver.getNextSensorEvent()) != null) {
            eventHandler.handleEvent(event);
        }
    }

}
