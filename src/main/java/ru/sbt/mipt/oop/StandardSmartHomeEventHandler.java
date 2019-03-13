package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.*;

public class StandardSmartHomeEventHandler implements SmartHomeEventHandler {
    private SmartHome smartHome;
    private SensorCommandSender sensorCommandSender;
    private SmartHomeEventProcessor doorEventProcessor;
    private SmartHomeEventProcessor lightEventProcessor;

    public StandardSmartHomeEventHandler(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        this.smartHome = smartHome;
        this.sensorCommandSender = sensorCommandSender;
        doorEventProcessor = new AdvancedDoorEventProcessor(smartHome, sensorCommandSender);
        lightEventProcessor = new LightEventProcessor(smartHome, sensorCommandSender);
    }

    public void handleEvent(SensorEvent event) {
        System.out.println("Got event: " + event);
        SensorEventType eventType = event.getType();
        if (eventType == LIGHT_ON || eventType == LIGHT_OFF) {
            lightEventProcessor.process(event.getObjectId(), eventType);
        }
        else if (eventType == DOOR_CLOSED && eventType == DOOR_OPEN) {
            doorEventProcessor.process(event.getObjectId(), eventType);
        }
    }

}
