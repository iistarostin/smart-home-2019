package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.*;

public class StandardSmartHomeEventHandler implements SmartHomeEventHandler {
    private SmartHome smartHome;
    private SensorCommandSender sensorCommandSender;
    private StandardDoorEventProcessor doorEventProcessor;
    private HallDoorEventProcessor hallDoorEventProcessor;
    private LightEventProcessor lightEventProcessor;
    private AlarmEventProcessor alarmEventProcessor;

    public StandardSmartHomeEventHandler(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        this.smartHome = smartHome;
        this.sensorCommandSender = sensorCommandSender;
        hallDoorEventProcessor = new HallDoorEventProcessor(smartHome, sensorCommandSender);
        doorEventProcessor = new StandardDoorEventProcessor(smartHome);
        lightEventProcessor = new LightEventProcessor(smartHome);
        alarmEventProcessor = new AlarmEventProcessor(smartHome);
    }

    public void handleEvent(SensorEvent event) {
        System.out.println("Got event: " + event);
        SensorEventType eventType = event.getType();
        String elementID = event.getObjectId();
        if (eventType == LIGHT_ON || eventType == LIGHT_OFF) {
            if (eventType == LIGHT_ON) {
                lightEventProcessor.turnLightOn(elementID);
            }
            if (eventType == LIGHT_OFF) {

                lightEventProcessor.turnLightOff(elementID);
            }
        }
        else if (eventType == DOOR_CLOSED || eventType == DOOR_OPEN) {
            if (eventType == DOOR_CLOSED && event.getObjectId() == "hall") {
                hallDoorEventProcessor.closeDoor(elementID);
            }
            else {
                if (eventType == DOOR_OPEN) {
                    doorEventProcessor.openDoor(elementID);
                }
                if (eventType == DOOR_CLOSED) {
                    doorEventProcessor.closeDoor(elementID);
                }
            }
        }
        else if (eventType == ALARM_ACTIVATE || eventType == ALARM_DEACTIVATE) {
            String code = (String)event.getParameter();
            if (eventType == ALARM_ACTIVATE) {
                alarmEventProcessor.activateAlarm(elementID, code);
            }
            if (eventType == ALARM_DEACTIVATE) {
                if (!alarmEventProcessor.deactivateAlarm(elementID, code)) {
                    alarmEventProcessor.raiseAlarm(elementID);
                }
            }
        }
    }

}
