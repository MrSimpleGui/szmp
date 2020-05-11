package com.webdrp.entity;


import com.webdrp.common.BaseBean;

public class SenicSpot  extends BaseBean {

    private String senicId;

    private String name;

    private String longitude;

    private String latitude;

    public String getSenicId() {
        return senicId;
    }

    public void setSenicId(String senicId) {
        this.senicId = senicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
