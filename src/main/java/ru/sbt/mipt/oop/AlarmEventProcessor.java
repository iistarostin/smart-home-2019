package ru.sbt.mipt.oop;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class AlarmEventProcessor {
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

    public boolean deactivateAlarm(String elementID, String code) {
        AtomicBoolean wrongCode = new AtomicBoolean(false);
        smartHome.applyComposite(elementID, new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement current) {
                if (current instanceof Alarm) {
                    Alarm alarm = (Alarm) current;
                    try {
                        alarm.deactivate(code);
                        System.out.println("Alarm deactivated with code " + code);
                    }
                    catch (InvalidCodeException e) {
                        wrongCode.set(true);
                        System.out.println("Failed to deactivate, invalid code");
                    }
                }
            }
        });
        return wrongCode.get();
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
}
