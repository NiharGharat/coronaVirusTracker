package com.nihar.projects.coronavirustracker.model;

/**
 * To save the data
 */
public class LocationBean {

    private String province;
    private String state;
    private int totalCases;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }
}
