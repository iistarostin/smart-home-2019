package ru.sbt.mipt.oop;
import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;

import java.util.HashMap;

public class EventHandlerCCAPIAdapter implements EventHandler {
    SmartHomeEventHandler base;
    static HashMap<String, SensorEventType> typeHashMap;
    public EventHandlerCCAPIAdapter(SmartHomeEventHandler base) {
        this.base = base;
        if (typeHashMap == null) {
            typeHashMap = new HashMap<>();
            typeHashMap.put("LightIsOn", SensorEventType.LIGHT_ON);
            typeHashMap.put("LightIsOff", SensorEventType.LIGHT_OFF);
            typeHashMap.put("DoorIsOpen", SensorEventType.DOOR_OPEN);
            typeHashMap.put("DoorIsClosed", SensorEventType.DOOR_CLOSED);
            typeHashMap.put("DoorIsLocked", null);
            typeHashMap.put("DoorIsUnlocked", null);
        }
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        SensorEvent smartHomeEvent = convertEventFormat(event);
        if (smartHomeEvent.getType() != null) {
            base.handleEvent(smartHomeEvent);
        }
    }

    private SensorEvent convertEventFormat(CCSensorEvent event) {
        return new SensorEvent(typeHashMap.get(event.getEventType()), event.getObjectId());
    }
}
