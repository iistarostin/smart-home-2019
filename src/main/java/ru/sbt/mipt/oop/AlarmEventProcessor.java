package ru.sbt.mipt.oop;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static ru.sbt.mipt.oop.SensorEventType.ALARM_ACTIVATE;
import static ru.sbt.mipt.oop.SensorEventType.ALARM_DEACTIVATE;

public class AlarmEventProcessor implements SmartHomeEventHandler {
    SmartHome smartHome;
    public  AlarmEventProcessor(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    public void activateAlarm(String elementID, String code) {
        smartHome.applyComposite(elementID, new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement current) {
                if (current instanceof Alarm) {
                    Alarm alarm = (Alarm) current;
                    alarm.activate(code);
                    System.out.println("Alarm activated with code " + code);
                }
            }
        });
    }

    public void deactivateAlarm(String elementID, String code) {
        smartHome.applyComposite(elementID, new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement current) {
                if (current instanceof Alarm) {
                    Alarm alarm = (Alarm) current;
                    alarm.deactivate(code);
                    if (alarm.getState() == AlarmState.INACTIVE) {
                        System.out.println("Alarm deactivated with code " + code);
                    }
                    else {
                        System.out.println("Failed to deactivate, invalid code");
                    }
                }
            }
        });
    }

    public void raiseAlarm(String elementID) {
        smartHome.applyComposite(elementID, new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement current) {
                if (current instanceof Alarm) {
                    Alarm alarm = (Alarm) current;
                    alarm.raise();
                    System.out.println("Alarm raised! Call the police!!!");
                }
            }
        });
    }

    @Override
    public void handleEvent(SensorEvent event) {
        SensorEventType eventType = event.getType();
        String elementID = event.getObjectId();
        if (eventType == ALARM_ACTIVATE || eventType == ALARM_DEACTIVATE) {
            String code = (String)event.getParameter();
            if (eventType == ALARM_ACTIVATE) {
                activateAlarm(elementID, code);
            }
            if (eventType == ALARM_DEACTIVATE) {
                deactivateAlarm(elementID, code);
            }
        }
    }
}
