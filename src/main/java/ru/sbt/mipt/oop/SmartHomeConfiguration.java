package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SmartHomeConfiguration {
    private SmartHome smartHome;
    private SensorEventsManager sensorEventsManager;
    public SmartHomeConfiguration() throws IOException {
        SmartHomeJSONSerializer smartHomeJSONSerializer = new SmartHomeJSONSerializer();
        //JSON version doesn't have an Alarm
        //smartHome = smartHomeJSONSerializer.readSmartHome();
        smartHome = HomeBuilder.generateSmartHome();
        sensorEventsManager = getCCEventManager(smartHome);

    }

    private SensorEventsManager getCCEventManager(SmartHome smartHome) {
        SensorCommandSender sensorCommandSender = new DummyCommandSender();

        SensorEventsManager sensorEventsManager = new SensorEventsManager();
        List<SmartHomeEventHandler> handlers = createHandlers(smartHome, sensorCommandSender);
        registerHandlers(handlers, sensorEventsManager);

        ProgrammableRemoteControl remoteControl = new ProgrammableRemoteControl();
        programRC(remoteControl, smartHome, sensorCommandSender);

        return sensorEventsManager;
    }

    private void programRC(ProgrammableRemoteControl remoteControl, SmartHome smartHome, SensorCommandSender sensorCommandSender) {
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

    private static void registerHandlers(List<SmartHomeEventHandler> handlers, SensorEventsManager sensorEventsManager) {
        for (SmartHomeEventHandler handler : handlers) {
            sensorEventsManager.registerEventHandler(new EventHandlerCCAPIAdapter(handler));
        }
    }

    @Bean
    @Description("smartHome")
    public SmartHome getSmartHome() {
        return smartHome;
    }

    @Bean
    @Description("eventManager")
    public SensorEventsManager getSensorEventsManager() {
        return sensorEventsManager;
    }
}
