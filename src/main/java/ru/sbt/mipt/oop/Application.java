package ru.sbt.mipt.oop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.coolcompany.smarthome.events.SensorEventsManager;

public class Application {

    public static void main(String... args) throws IOException {
        //SmartHome smartHome = new SmartHomeJSONSerializer().readSmartHome();
        SmartHome smartHome = HomeBuilder.generateSmartHome();
        //runStandardEventManager(smartHome);
        runCCEventManager(smartHome);
    }

    private static void runStandardEventManager(SmartHome smartHome) {
        // начинаем цикл обработки событий
        SensorEventReceiver sensorEventReceiver = new SensorEventGenerator();
        SmartHomeEventManager smartHomeEventManager = new SmartHomeEventManager(sensorEventReceiver);
        List<SmartHomeEventHandler> handlers = createHandlers(smartHome, new DummyCommandSender());
        registerHandlers(handlers, smartHomeEventManager);
        smartHomeEventManager.start();
    }

    private static void runCCEventManager(SmartHome smartHome) {
        // начинаем цикл обработки событий
        SensorEventsManager sensorEventsManager = new SensorEventsManager();
        List<SmartHomeEventHandler> handlers = createHandlers(smartHome, new DummyCommandSender());
        registerHandlers(handlers, sensorEventsManager);
        sensorEventsManager.start();
    }

    private static List<SmartHomeEventHandler> createHandlers(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        List<SmartHomeEventHandler> handlers = new ArrayList<>();
        handlers.add(new GuardedSmartHomeEventHandler(new LightEventProcessor(smartHome), smartHome));
        handlers.add(new GuardedSmartHomeEventHandler(new StandardDoorEventProcessor(smartHome), smartHome));
        handlers.add(new GuardedSmartHomeEventHandler(new HallDoorEventProcessor(smartHome, sensorCommandSender), smartHome));
        handlers.add(new GuardedSmartHomeEventHandler(new AlarmEventProcessor(smartHome), smartHome));
        return handlers;
    }

    private static void registerHandlers(List<SmartHomeEventHandler> handlers, SmartHomeEventManager smartHomeEventManager) {
        for (SmartHomeEventHandler handler : handlers) {
            smartHomeEventManager.addEventHandler(handler);
        }
    }

    private static void registerHandlers(List<SmartHomeEventHandler> handlers, SensorEventsManager sensorEventsManager) {
        for (SmartHomeEventHandler handler : handlers) {
            sensorEventsManager.registerEventHandler(new EventHandlerCCAPIAdapter(handler));
        }
    }
}
