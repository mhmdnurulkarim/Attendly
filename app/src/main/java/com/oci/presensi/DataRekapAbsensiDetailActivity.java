package com.oci.presensi;

import static com.oci.presensi.util.Utils.getPeriode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.oci.presensi.adapter.AdapterDataRekapAbsensiDetail;
import com.oci.presensi.databinding.ActivityDataRekapAbsensiDetailBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAbsensi;
import com.oci.presensi.model.ModelAbsensiFetch;
import com.oci.presensi.model.ModelAkun;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRekapAbsensiDetailActivity extends AppCompatActivity {

    private ActivityDataRekapAbsensiDetailBinding binding;
    private DataHelper dbHelper;
    private ModelAkun akun;
    private List<ModelAbsensi> listAbsensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataRekapAbsensiDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        int id = getIntent().getIntExtra("idUser", 0);

        dbHelper = new DataHelper(this);
        dbHelper.getAkunFromFirebase();
        dbHelper.getAttendanceFromFirestore();
        listAbsensi = dbHelper.getAllAbsensiByIdUser(id);
        akun = dbHelper.getAkun(id);

        setupUI();
        setupRecyclerView();
        setupPrintButton();
        setupBackPressedHandler();
    }

    private void setupUI() {
        binding.txtPeriode.setText("Periode " + getPeriode());
        binding.txtIDKaryawan.setText("ID Karyawan : " + akun.getIdUser());
        binding.txtNamaKaryawan.setText("Nama Karyawan : " + akun.getNama());
        binding.txtNikKaryawan.setText("NIK Karyawan : " + akun.getNik());
        binding.txtDivisiKaryawan.setText("Divisi Karyawan : " + akun.getDivisi());
        binding.txtRoleKaryawan.setText("Role Karyawan : " + akun.getId_role());
    }

    private void setupRecyclerView() {
        if (!listAbsensi.isEmpty()) {
            List<ModelAbsensiFetch> processedData = prepareAbsensiData(listAbsensi);
            AdapterDataRekapAbsensiDetail itemList = new AdapterDataRekapAbsensiDetail(processedData, dbHelper);
            binding.rvDataRekapAbsensiDetail.setLayoutManager(new LinearLayoutManager(this));
            binding.rvDataRekapAbsensiDetail.setAdapter(itemList);
        } else {
            showNoDataMessage();
        }
    }

    private List<ModelAbsensiFetch> prepareAbsensiData(List<ModelAbsensi> rawAbsensiList) {
        Map<Integer, ModelAbsensiFetch> absensiMap = new HashMap<>();

        for (ModelAbsensi absensi : rawAbsensiList) {
            int userId = absensi.getId_user();
            ModelAbsensiFetch absensiFetch = absensiMap.get(userId);

            if (absensiFetch == null) {
                absensiFetch = new ModelAbsensiFetch(userId, null, null);
            }

            if (absensi.getKeterangan().equalsIgnoreCase("DATANG")) {
                absensiFetch = new ModelAbsensiFetch(userId, absensi.getTimestamp(), absensiFetch.getPulangTimestamp());
            } else if (absensi.getKeterangan().equalsIgnoreCase("PULANG")) {
                absensiFetch = new ModelAbsensiFetch(userId, absensiFetch.getDatangTimestamp(), absensi.getTimestamp());
            }

            absensiMap.put(userId, absensiFetch);
        }

        return new ArrayList<>(absensiMap.values());
    }

    private void setupPrintButton() {
        binding.print.setOnClickListener(v -> {
            if (listAbsensi == null || listAbsensi.isEmpty()) {
                showNoDataMessage();
            } else {
                checkPermissionAndPrint();
            }
        });
    }

    private void savePdf(ModelAkun akun, List<ModelAbsensi> listAbsensi) {
        try {
            String fileName = "RekapAbsensi_" + akun.getNama() + ".pdf";

            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/Presensi");

            Uri uri = getContentResolver().insert(MediaStore.Files.getContentUri("external"), contentValues);
            if (uri == null) {
                throw new IOException("Failed to create new MediaStore record.");
            }

            OutputStream outputStream = getContentResolver().openOutputStream(uri);
            if (outputStream == null) {
                throw new IOException("Failed to get output stream.");
            }

            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Rekap Absensi"));
            document.add(new Paragraph("Nama: " + akun.getNama()));
            document.add(new Paragraph("ID: " + akun.getIdUser()));
            document.add(new Paragraph("Divisi: " + akun.getDivisi()));
            document.add(new Paragraph("Periode: " + getPeriode()));
            document.add(new Paragraph(" "));

            for (ModelAbsensi absensi : listAbsensi) {
                document.add(new Paragraph("Tanggal: " + absensi.getTimestamp() +
                        " - Keterangan: " + absensi.getKeterangan()));
            }

            document.close();
            outputStream.close();
            Toast.makeText(this, "PDF disimpan di Download", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal membuat PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private void checkPermissionAndPrint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            savePdf(akun, listAbsensi);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                savePdf(akun, listAbsensi);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void showNoDataMessage() {
        Toast.makeText(this, "Tidak ada data absensi yang tersedia.", Toast.LENGTH_SHORT).show();
    }

    private void setupBackPressedHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }
}