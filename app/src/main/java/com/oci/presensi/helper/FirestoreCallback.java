package com.oci.presensi.helper;

import com.oci.presensi.model.ModelAbsensi;

import java.util.List;

public interface FirestoreCallback {
    void onCallback(List<ModelAbsensi> attendanceList);
}