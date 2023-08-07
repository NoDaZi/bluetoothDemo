package com.example.bledemo.Bluetooth;

public class DeviceData {

    String Address;
    String Alias ; // Build.VERSION_CODES.R 이상
    String Name ;
    int type;

    //생성자
    public DeviceData(String Address, String Alias, String Name, int type){
        this.Address = Address;
        this.Alias = Alias;
        this.Name = Name;
        this.type = type;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
