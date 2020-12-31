package com.marapps.api;

public class Institute {
    int instituteId;
    String instituteCode;
    String name;
    String city;
    String url;
    String advertisingUrl;
    String informationImageUrl;
    String informationUrl;

    public void print(){
        System.out.println("----------------------------");
        System.out.println("instituteId: " + instituteId);
        System.out.println("instituteCode: " + instituteCode);
        System.out.println("name: " + name);
        System.out.println("city: " + city);
        System.out.println("url: " + url);
        System.out.println("advertisingUrl: " + advertisingUrl);
        System.out.println("informationImageUrl: " + informationImageUrl);
        System.out.println("informationUrl: " + informationUrl);
        System.out.println("----------------------------");
        System.out.println("");
    }

    public int getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(int instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteCode() {
        return instituteCode;
    }

    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdvertisingUrl() {
        return advertisingUrl;
    }

    public void setAdvertisingUrl(String advertisingUrl) {
        this.advertisingUrl = advertisingUrl;
    }

    public String getInformationImageUrl() {
        return informationImageUrl;
    }

    public void setInformationImageUrl(String informationImageUrl) {
        this.informationImageUrl = informationImageUrl;
    }

    public String getInformationUrl() {
        return informationUrl;
    }

    public void setInformationUrl(String informationUrl) {
        this.informationUrl = informationUrl;
    }
}