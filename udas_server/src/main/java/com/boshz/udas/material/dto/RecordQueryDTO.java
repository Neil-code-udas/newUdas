package com.boshz.udas.material.dto;

public class RecordQueryDTO {
    private String startTime;
    private String endTime;
    private String materialName;
    private String changeType;
    private String usePerson;

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getMaterialName() {
        return materialName;
    }
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    public String getChangeType() {
        return changeType;
    }
    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }
    public String getUsePerson() {
        return usePerson;
    }
    public void setUsePerson(String usePerson) {
        this.usePerson = usePerson;
    }
}