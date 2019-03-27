package ru.sbt.mipt.oop;

import java.io.IOException;

public class Application {

    public static void main(String... args) throws IOException {
        //SmartHome smartHome = new SmartHomeJSONSerializer().readSmartHome();
        SmartHome smartHome = HomeBuilder.generateSmartHome();
        applicationLoop(smartHome);
    }

    private static void applicationLoop(SmartHome smartHome) {
        // начинаем цикл обработки событий
        SensorEventReceiver sensorEventReceiver = new SensorEventGenerator();
        SmartHomeEventManager smartHomeEventManager = new SmartHomeEventManager(sensorEventReceiver);
        createHandlers(smartHome, smartHomeEventManager, new DummyCommandSender());
        smartHomeEventManager.start();
    }

    private static void createHandlers(SmartHome smartHome, SmartHomeEventManager smartHomeEventManager, SensorCommandSender sensorCommandSender) {
        smartHomeEventManager.addEventHandler(new GuardedSmartHomeEventHandler(new LightEventProcessor(smartHome), smartHome));
        smartHomeEventManager.addEventHandler(new GuardedSmartHomeEventHandler(new StandardDoorEventProcessor(smartHome), smartHome));
        smartHomeEventManager.addEventHandler(new GuardedSmartHomeEventHandler(new HallDoorEventProcessor(smartHome, sensorCommandSender), smartHome));
        smartHomeEventManager.addEventHandler(new GuardedSmartHomeEventHandler(new AlarmEventProcessor(smartHome), smartHome));
    }

}
