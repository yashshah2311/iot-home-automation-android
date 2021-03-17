package model;

public class Device {
//    private int index;
    private String deviceName;
    private String defaultValue;
    private String actualValue;

    public Device() {
    }

//    public Device(int index, String deviceName, String defaultValue, String actualValue) {
//        this.index = index;
//        this.deviceName = deviceName;
//        this.defaultValue = defaultValue;
//        this.actualValue = actualValue;
//    }

    public Device(String deviceName, String defaultValue, String actualValue) {
        this.deviceName = deviceName;
        this.defaultValue = defaultValue;
        this.actualValue = actualValue;
    }

//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    @Override
    public String toString() {
        return "Device{" +
                " deviceName='" + deviceName + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", actualValue='" + actualValue + '\'' +
                '}';
    }
}
