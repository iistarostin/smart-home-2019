package ru.sbt.mipt.oop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.coolcompany.smarthome.events.SensorEventsManager;
import rc.RemoteControl;

public class Application {

    public static void main(String... args) throws IOException {
        //SmartHome smartHome = new SmartHomeJSONSerializer().readSmartHome();
        SmartHome smartHome = HomeBuilder.generateSmartHome();
        //runStandardEventManager(smartHome);
        runCCEventManager(smartHome);
    }

    private static void runStandardEventManager(SmartHome smartHome) {
        // начинаем цикл обработки событий
        SensorCommandSender sensorCommandSender = new DummyCommandSender();

        SmartHomeEventManager smartHomeEventManager = new SmartHomeEventManager(new SensorEventGenerator());
        List<SmartHomeEventHandler> handlers = createHandlers(smartHome, sensorCommandSender);
        registerHandlers(handlers, smartHomeEventManager);

        //Initialize and program remote control
        ProgrammableRemoteControl remoteControl = new ProgrammableRemoteControl();
        programRC(remoteControl, smartHome, sensorCommandSender);

        smartHomeEventManager.start();
    }

    private static void runCCEventManager(SmartHome smartHome) {
        // начинаем цикл обработки событий
        SensorCommandSender sensorCommandSender = new DummyCommandSender();

        SensorEventsManager sensorEventsManager = new SensorEventsManager();
        List<SmartHomeEventHandler> handlers = createHandlers(smartHome, sensorCommandSender);
        registerHandlers(handlers, sensorEventsManager);

        ProgrammableRemoteControl remoteControl = new ProgrammableRemoteControl();
        programRC(remoteControl, smartHome, sensorCommandSender);

        sensorEventsManager.start();
    }

    private static void programRC(ProgrammableRemoteControl remoteControl, SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        remoteControl.bind("A", new ControlCommandAlarmRaise(smartHome.getAlarm(), sensorCommandSender));
        remoteControl.bind("B", new ControlCommandAlarmActivate(smartHome.getAlarm(), sensorCommandSender, ""));
        remoteControl.bind("3", new ControlCommandAllLightsOff(smartHome, sensorCommandSender));
        remoteControl.bind("4", new ControlCommandCorridorLightOn(smartHome, sensorCommandSender));
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
