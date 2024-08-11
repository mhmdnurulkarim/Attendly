package com.oci.presensi.model;

public class ModelAbsensiFetch {
    int userId;
    String datangTimestamp, pulangTimestamp;

    public ModelAbsensiFetch(int userId, String datangTimestamp, String pulangTimestamp) {
        this.userId = userId;
        this.datangTimestamp = datangTimestamp;
        this.pulangTimestamp = pulangTimestamp;
    }

    public int getUserId() {
        return userId;
    }

    public String getDatangTimestamp() {
        return datangTimestamp;
    }

    public String getPulangTimestamp() {
        return pulangTimestamp;
    }
}
