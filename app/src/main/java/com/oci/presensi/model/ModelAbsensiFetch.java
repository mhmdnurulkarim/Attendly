package com.oci.presensi.model;

public class ModelAbsensiFetch {
    int userId;
    String keteranganDatang, datangTimestamp, KetaranganPulang, pulangTimestamp;

    public ModelAbsensiFetch(int userId, String keteranganDatang, String datangTimestamp, String KetaranganPulang, String pulangTimestamp) {
        this.userId = userId;
        this.keteranganDatang = keteranganDatang;
        this.datangTimestamp = datangTimestamp;
        this.KetaranganPulang = KetaranganPulang;
        this.pulangTimestamp = pulangTimestamp;
    }

    public int getUserId() {
        return userId;
    }

    public String getKeteranganDatang() {
        return keteranganDatang;
    }

    public String getDatangTimestamp() {
        return datangTimestamp;
    }

    public String getKetaranganPulang() {
        return KetaranganPulang;
    }

    public String getPulangTimestamp() {
        return pulangTimestamp;
    }
}
