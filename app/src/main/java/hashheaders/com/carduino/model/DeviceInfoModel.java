package hashheaders.com.carduino.model;

/**
 * Created by Ashwani on 10-06-2017.
 */

public class DeviceInfoModel {
    private String deviceName;
    private String macAddr;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }
}
