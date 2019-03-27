package ru.sbt.mipt.oop;

public class SensorEvent {
    private final SensorEventType type;
    private final String objectId;
    private final Object parameter;

    public SensorEvent(SensorEventType type, String objectId) {
        this.type = type;
        this.objectId = objectId;
        this.parameter = null;
    }

    public SensorEvent(SensorEventType type, String objectId, Object parameter) {
        this.type = type;
        this.objectId = objectId;
        this.parameter = parameter;
    }

    public SensorEventType getType() {
        return type;
    }

    public String getObjectId() {
        return objectId;
    }

    public Object getParameter() {
        return parameter;
    }

    @Override
    public String toString() {
        return "SensorEvent{" +
                "type=" + type +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}
